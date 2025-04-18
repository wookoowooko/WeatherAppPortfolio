package io.wookoo.cities.mvi

import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.repo.IDeleteForecastsRepo
import io.wookoo.domain.repo.IGeoRepo
import io.wookoo.domain.repo.ILocationProvider
import io.wookoo.domain.service.IConnectivityObserver
import io.wookoo.domain.usecases.MapCurrentForecastAsCityUseCase
import io.wookoo.domain.utils.AppError
import io.wookoo.domain.utils.DataError
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesStore @Inject constructor(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    reducer: CitiesReducer,
    private val currentForecast: ICurrentForecastRepo,
    private val geoRepository: IGeoRepo,
    private val locationProvider: ILocationProvider,
    private val deleteForecastsRepo: IDeleteForecastsRepo,
    private val mapCurrentForecastAsCityUseCase: MapCurrentForecastAsCityUseCase,
    networkMonitor: IConnectivityObserver,
) : Store<CitiesState, CitiesIntent, CitiesSideEffect>(
    initialState = CitiesState(),
    reducer = reducer,
    storeScope = storeScope
) {

    private var searchJob: Job? = null

    private val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .onEach {
            dispatch(OnUpdateNetworkState(it))
        }
        .stateIn(
            storeScope,
            started = SharingStarted.Eagerly,
            false
        )

    override fun initializeObservers() {
        observeSearchQuery()
        observeCities()
    }

    override fun handleSideEffects(intent: CitiesIntent) {
        when (intent) {
            is OnGPSClick -> storeScope.launch { getGeolocationFromGpsSensors() }
            is OnSearchedGeoItemCardClick ->
                emitSideEffect(
                    CitiesSideEffect.OnSyncRequest(
                        geoItemId = intent.geoItem.geoItemId
                    )
                )

            is OnDeleteCity -> storeScope.launch { deleteCity(intent.geoItemId) }
            is OnUpdateCurrentGeo ->
                emitSideEffect(
                    CitiesSideEffect.OnSyncRequest(
                        geoItemId = intent.geoItemId,
                        isNeedToUpdate = true
                    )
                )

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
        currentForecast.getAllCurrentForecastLocations()
            .onEach { listOfCitiesEntity ->
                dispatch(OnCitiesLoaded(listOfCitiesEntity.map { mapCurrentForecastAsCityUseCase(it) }))
            }
            .launchIn(storeScope)
    }

    // Functions
    private fun searchLocationFromApi(query: String) = storeScope.launch {
        dispatch(OnSearchQuery)
        geoRepository.searchLocationFromApiByQuery(query)
            .onSuccess { searchResults ->
                dispatch(OnSuccessSearchLocation(results = searchResults.results))
            }
            .onError { error ->
                dispatch(OnErrorSearchLocation)
                emitSideEffect(CitiesSideEffect.ShowSnackBar(error))
            }
            .onFinally {
                dispatch(OnSearchQueryDone)
            }
    }

    private suspend fun getGeolocationFromGpsSensors() {
        if (isOffline.value) {
            emitSideEffect(CitiesSideEffect.ShowSnackBar(DataError.Remote.NO_INTERNET))
        } else {
            locationProvider.getGeolocationFromGpsSensors().first()
                .onSuccess { geoLocation ->
                    fetchReversGeocoding(
                        latitude = geoLocation.first,
                        longitude = geoLocation.second
                    )
                }.onError { geoError: AppError ->
                    dispatch(OnErrorUpdateGeolocationFromGpsSensors)
                    emitSideEffect(CitiesSideEffect.ShowSnackBar(geoError))
                    emitSideEffect(CitiesSideEffect.OnShowSettingsDialog(geoError))
                }
        }
    }

    private fun fetchReversGeocoding(
        latitude: Double,
        longitude: Double,
    ) = storeScope.launch {
        geoRepository.getReverseGeocodingLocation(
            latitude,
            longitude
        )
            .onSuccess { gpsItems ->
                gpsItems.results.firstOrNull()?.let { geoName ->
                    dispatch(OnSuccessFetchReversGeocodingFromApi(geoName))
                } ?: run {
                    dispatch(OnErrorFetchReversGeocodingFromApi)
                    emitSideEffect(CitiesSideEffect.ShowSnackBar(DataError.Remote.LOCATION_NOT_FOUND))
                }
            }
            .onError { apiError ->
                dispatch(OnErrorFetchReversGeocodingFromApi)
                emitSideEffect(CitiesSideEffect.ShowSnackBar(apiError))
            }
    }

    private suspend fun deleteCity(geoItemId: Long) {
        dispatch(OnLoading)
        deleteForecastsRepo.deleteCityWithCurrentAndWeeklyForecasts(geoItemId)
            .onError { dataError ->
                emitSideEffect(CitiesSideEffect.ShowSnackBar(dataError))
            }
            .onFinally {
                dispatch(OnLoadingFinish)
            }
    }

    override fun clear() {
        storeScope.cancel()
    }

    companion object {
        private const val THRESHOLD = 500L
    }
}
