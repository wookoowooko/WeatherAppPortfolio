package io.wookoo.cities.mvi

import android.util.Log
import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.settings.UserSettingsModel
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesStore @Inject constructor(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    reducer: CitiesReducer,
    private val masterRepository: IMasterWeatherRepo,
    private val dataStore: IDataStoreRepo,
) : Store<CitiesState, CitiesIntent, CitiesSideEffect>(
    initialState = CitiesState(),
    reducer = reducer,
    storeScope = storeScope,
) {

    private var searchJob: Job? = null

    private val userSettings = dataStore.userSettings.stateIn(
        storeScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = UserSettingsModel()
    )

    override fun initializeObservers() {
        observeSearchQuery()
        observeCities()
        observeUserGeolocationChanges()
    }

    override fun handleSideEffects(intent: CitiesIntent) {
        when (intent) {
            is OnSearchedGeoItemCardClick -> storeScope.launch { onSearchedGeoItemCardClick(intent) }
            else -> Unit
        }
    }

    private fun observeCities() {
        masterRepository.getAllCitiesCurrentWeather()
            .onEach { listOfCities: List<CurrentWeatherResponseModel> ->
                dispatch(OnCitiesLoaded(listOfCities))
            }
            .launchIn(storeScope)
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


    // Observers
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

    // Functions
    private fun searchLocationFromApi(query: String) = storeScope.launch {
        dispatch(OnLoading)
        masterRepository.getSearchedLocation(query, language = "ru")
            .onSuccess { searchResults ->
                dispatch(OnSuccessSearchLocation(results = searchResults.results))
            }
            .onError { error ->
                dispatch(OnErrorSearchLocation)
                emitSideEffect(CitiesSideEffect.ShowSnackBar(error))
            }
            .onFinally {
                dispatch(OnLoadingFinish)
            }
    }

    private fun synchronizeWeather(latitude: Double, longitude: Double) {
        storeScope.launch {
            masterRepository.syncCurrentWeather(
                latitude = latitude,
                longitude = longitude
            ).onError { apiError ->
                Log.d(TAG, "synchronizeWeather: $apiError")
                emitSideEffect(CitiesSideEffect.ShowSnackBar(apiError))
            }
        }
    }

    private suspend fun onSearchedGeoItemCardClick(intent: OnSearchedGeoItemCardClick) {
        Log.d(TAG, "onSearchedGeoItemCardClick: вызван")
        dispatch(OnLoading)
        dataStore.saveUserLocation(
            latitude = intent.geoItem.latitude,
            longitude = intent.geoItem.longitude,
        )
            .asEmptyDataResult()
            .onError { prefError ->
                emitSideEffect(CitiesSideEffect.ShowSnackBar(prefError))
            }
            .onFinally {
                dispatch(OnLoadingFinish)
                dispatch(OnChangeBottomSheetVisibility(false))
            }
    }

    override fun clear() {
        storeScope.cancel()
    }

    companion object {
        private const val THRESHOLD = 500L
        private const val TAG = "CitiesStore"
    }
}