package io.wookoo.cities.mvi

import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.onError
import io.wookoo.domain.utils.onFinally
import io.wookoo.domain.utils.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesStore @Inject constructor(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    reducer: CitiesReducer,
    private val masterRepository: IMasterWeatherRepo,
) : Store<CitiesState, CitiesIntent, CitiesSideEffect>(
    initialState = CitiesState(),
    reducer = reducer,
    storeScope = storeScope,
) {

    private var searchJob: Job? = null

    override fun initializeObservers() {
        observeSearchQuery()
        observeCities()
    }

    override fun handleSideEffects(intent: CitiesIntent) {
        when (intent) {
            is OnSearchedGeoItemCardClick -> storeScope.launch { syncWeather(intent) }
            else -> Unit
        }
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

    private fun observeCities() {
        masterRepository.getAllCitiesCurrentWeather()
            .onEach { listOfCities: List<CurrentWeatherResponseModel> ->
                dispatch(OnCitiesLoaded(listOfCities))
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

    private suspend fun syncWeather(intent: OnSearchedGeoItemCardClick) {
        masterRepository.synchronizeCurrentWeather(
            latitude = intent.geoItem.latitude,
            longitude = intent.geoItem.longitude,
            geoItemId = intent.geoItem.geoItemId,
            countryName = intent.geoItem.country,
            cityName = intent.geoItem.cityName
        )
    }


    override fun clear() {
        storeScope.cancel()
    }

    companion object {
        private const val THRESHOLD = 500L
        private const val TAG = "CitiesStore"
    }
}
