package io.wookoo.main.mvi

import android.util.Log
import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.ILocationProvider
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.settings.UserSettingsModel
import io.wookoo.domain.utils.AppError
import io.wookoo.domain.utils.asEmptyDataResult
import io.wookoo.domain.utils.onError
import io.wookoo.domain.utils.onFinally
import io.wookoo.domain.utils.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPageStore @Inject constructor(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    private val dataStore: IDataStoreRepo,
    private val weatherLocationManager: ILocationProvider,
    private val masterRepository: IMasterWeatherRepo,
    reducer: MainPageReducer,
) : Store<MainPageState, MainPageIntent, MainPageEffect>(
    storeScope = storeScope,
    initialState = MainPageState(),
    reducer = reducer,
) {
    private var searchJob: Job? = null
    private val userSettings = dataStore.userSettings.stateIn(
        storeScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = UserSettingsModel()
    )

    override fun initializeObservers() {
        observeSearchQuery()
        observeUserGeolocationChanges()
//        observeCurrentWeather()
        viewPagerCount
        observeCurrentWeather2()
    }

    override fun handleSideEffects(intent: MainPageIntent) {
        when (intent) {
            is OnGeolocationIconClick -> getGeolocationFromGpsSensors()
            is OnSearchedGeoItemCardClick -> storeScope.launch { onSearchedGeoItemCardClick(intent) }
            else -> Unit
        }
    }

    private val viewPagerCount: StateFlow<List<Long>> = masterRepository.getCurrentWeatherIds()
        .distinctUntilChanged()
        .onEach {
            Log.d(TAG, "listSize: $it")
            Log.d(TAG, "listSize: ${it.size}")
            dispatch(UpdateCityListCount(it.size))
        }
        .stateIn(
            storeScope,
            SharingStarted.Eagerly,
            initialValue = emptyList()
        )

//    fun observeGeoIds(){
//        masterRepository.getCurrentWeatherIds()
//            .filterNotNull()
//            .onEach {
//                dispatch(UpdateCityListCount(it.size))
//            }
//
//    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeCurrentWeather2() {
        viewPagerCount
            .filter { it.isNotEmpty() }
            .flatMapLatest { geoNameIds ->
                state.map { it.pagerPosition }
                    .distinctUntilChanged()
                    .flatMapLatest { position ->
                        masterRepository.currentWeather(geoNameIds[position])
                    }
            }
            .onEach { currentWeather ->
                dispatch(OnSuccessFetchCurrentWeatherFromApi(currentWeather))
            }
            .launchIn(storeScope)
    }

    private suspend fun onSearchedGeoItemCardClick(intent: OnSearchedGeoItemCardClick) {
        dispatch(OnLoading)
        dataStore.saveUserLocation(
            latitude = intent.geoItem.latitude,
            longitude = intent.geoItem.longitude,
        )
            .asEmptyDataResult()
            .onError { prefError ->
                emitSideEffect(MainPageEffect.OnShowSnackBar(prefError))
            }
            .onFinally {
                dispatch(OnLoadingFinish)
            }
    }

    private fun getGeolocationFromGpsSensors() {
        Log.d(TAG, "getGeolocationFromGpsSensors: вызван")
        weatherLocationManager.getGeolocationFromGpsSensors(
            onSuccessfullyLocationReceived = { lat, lon ->
                storeScope.launch {
                    dataStore.saveUserLocation(
                        latitude = lat,
                        longitude = lon
                    ).asEmptyDataResult()
                        .onSuccess {
                            Log.d(TAG, "getGeolocationFromGpsSensors: сохранено")
                            dispatch(OnSuccessfullyUpdateGeolocationFromGpsSensors)
                        }.onError { prefError ->
                            emitSideEffect(MainPageEffect.OnShowSnackBar(prefError))
                        }
                }
            },
            onError = { geoError: AppError ->
                dispatch(OnErrorUpdateGeolocationFromGpsSensors)
                emitSideEffect(MainPageEffect.OnShowSnackBar(geoError))
                emitSideEffect(MainPageEffect.OnShowSettingsDialog)
            }
        )
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(THRESHOLD)
            .onEach { query ->
                if (query.length >= 2) {
                    searchJob?.cancel()
                    searchJob = searchLocationFromApi(query)
                } else {
                    dispatch(OnQueryIsEmpty)
                }
            }
            .launchIn(storeScope)
    }

    private fun searchLocationFromApi(query: String) = storeScope.launch {
        dispatch(OnLoading)
        masterRepository.getSearchedLocation(query, language = "ru")
            .onSuccess { searchResults ->
                Log.d(TAG, "searchLocationFromApi: $searchResults")
                dispatch(OnSuccessSearchLocation(results = searchResults.results))
            }
            .onError { error ->
                dispatch(OnErrorSearchLocation)
                emitSideEffect(MainPageEffect.OnShowSnackBar(error))
            }
    }

    private fun observeUserGeolocationChanges() {
        userSettings
            .mapNotNull { setting ->
                setting.location.takeIf { it.latitude != 0.0 && it.longitude != 0.0 }
            }
            .distinctUntilChanged()
            .onEach { location ->
                Log.d(TAG, "start syncing weather: $location")
                synchronizeWeather(location.latitude, location.longitude)
            }
            .launchIn(storeScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeCurrentWeather() {
        userSettings
            .map { it.lastGeoName }
            .flatMapLatest { lastGeoName ->
                masterRepository.currentWeather(lastGeoName)
                    .onEach { currentWeather ->
                        dispatch(OnSuccessFetchCurrentWeatherFromApi(currentWeather))
                    }
            }
            .launchIn(storeScope)
    }

//    private fun fetchReversGeocoding(latitude: Double, longitude: Double) {
//        storeScope.launch {
//            dispatch(OnLoading)
//
//            masterRepository.getReverseGeocodingLocation(
//                latitude = latitude,
//                longitude = longitude,
//                language = "ru"
//            ).onSuccess { searchResults ->
//                dispatch(
//                    OnSuccessFetchReversGeocodingFromApi(
//                        city = searchResults.geonames.firstOrNull()?.name.orEmpty(),
//                        country = searchResults.geonames.firstOrNull()?.countryName.orEmpty()
//                    )
//                )
//            }.onError { apiError ->
//                println("fetchReversGeocoding failed: ${apiError.name}")
//                dispatch(OnErrorFetchReversGeocodingFromApi)
//                emitSideEffect(MainPageEffect.OnShowSnackBar(apiError))
//            }
//        }
//    }

    private fun synchronizeWeather(latitude: Double, longitude: Double) {
        storeScope.launch {
            masterRepository.syncCurrentWeather(
                latitude = latitude,
                longitude = longitude
            ).onError { apiError ->
                Log.d(TAG, "synchronizeWeather: $apiError")
                emitSideEffect(MainPageEffect.OnShowSnackBar(apiError))
            }
        }
    }

    private companion object {
        private const val THRESHOLD = 500L
        private const val TAG = "MainPageStore"
    }

    override fun clear() {
        storeScope.cancel()
    }
}
