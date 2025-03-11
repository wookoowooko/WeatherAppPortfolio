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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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
    }

    override fun handleSideEffects(intent: MainPageIntent) {
        when (intent) {
            is OnGeolocationIconClick -> getGeolocationFromGpsSensors()
            is OnSearchedGeoItemCardClick -> storeScope.launch { onSearchedGeoItemCardClick(intent) }
            else -> Unit
        }
    }

    private suspend fun onSearchedGeoItemCardClick(intent: OnSearchedGeoItemCardClick) {
        dispatch(OnLoading)
        dataStore.saveUserLocation(
            latitude = intent.geoItem.latitude,
            longitude = intent.geoItem.longitude
        ).asEmptyDataResult()
            .onError { prefError ->
                emitSideEffect(MainPageEffect.OnShowSnackBar(prefError))
            }
            .onFinally {
                dispatch(OnLoadingFinish)
            }
    }

    private fun getGeolocationFromGpsSensors() {
        weatherLocationManager.getGeolocationFromGpsSensors(
            onSuccessfullyLocationReceived = { lat, lon ->
                storeScope.launch {
                    dataStore.saveUserLocation(
                        latitude = lat,
                        longitude = lon
                    ).asEmptyDataResult()
                        .onSuccess {
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
        userSettings.map {
            it.location
        }.map {
            it.latitude to it.longitude
        }.distinctUntilChanged()
            .filter { (lat, lon) ->
                lat != 0.0 && lon != 0.0
            }
            .onEach { (lat, lon) ->
                fetchReversGeocoding(lat, lon)
                getCurrentWeather(lat, lon)
            }
            .launchIn(storeScope)
    }

    private fun fetchReversGeocoding(latitude: Double, longitude: Double) {
        storeScope.launch {
            dispatch(OnLoading)

            masterRepository.getReverseGeocodingLocation(
                latitude = latitude,
                longitude = longitude,
                language = "ru"
            ).onSuccess { searchResults ->
                dispatch(
                    OnSuccessFetchReversGeocodingFromApi(
                        city = searchResults.geonames.firstOrNull()?.name.orEmpty(),
                        country = searchResults.geonames.firstOrNull()?.countryName.orEmpty()
                    )
                )
            }.onError { apiError ->
                println("fetchReversGeocoding failed: ${apiError.name}")
                dispatch(OnErrorFetchReversGeocodingFromApi)
                emitSideEffect(MainPageEffect.OnShowSnackBar(apiError))
            }
        }
    }

    private fun getCurrentWeather(latitude: Double, longitude: Double) {
        dispatch(OnLoading)
        storeScope.launch {
            masterRepository.getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            ).onError { apiError ->
                emitSideEffect(MainPageEffect.OnShowSnackBar(apiError))
            }.onSuccess { response ->
                dispatch(OnSuccessFetchCurrentWeatherFromApi(response))
            }.onFinally {
                dispatch(OnLoadingFinish)
                //todo пересмотреть он файнали удаление
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
