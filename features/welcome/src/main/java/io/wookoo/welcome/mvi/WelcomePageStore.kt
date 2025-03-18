package io.wookoo.welcome.mvi

import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.ILocationProvider
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.service.IConnectivityObserver
import io.wookoo.domain.utils.AppError
import io.wookoo.domain.utils.DataError
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

class WelcomePageStore @Inject constructor(
    reducer: WelcomePageReducer,
    private val masterRepository: IMasterWeatherRepo,
    private val dataStore: IDataStoreRepo,
    private val weatherLocationManager: ILocationProvider,
    @StoreViewModelScope private val storeScope: CoroutineScope,
    networkMonitor: IConnectivityObserver,
) : Store<WelcomePageState, WelcomePageIntent, WelcomeSideEffect>(
    initialState = WelcomePageState(),
    storeScope = storeScope,
    reducer = reducer,
) {
    private var geonamesJob: Job? = null
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
        observeLocationChanges()
        observeSearchQuery()
    }

    override fun handleSideEffects(intent: WelcomePageIntent) {
        when (intent) {
            is OnSearchGeoLocationClick -> getGeolocationFromGpsSensors()
            is OnContinueButtonClick -> saveUserLocationToDataStore()
            is OnAppBarExpandChange ->
                if (state.value.isOffline) emitSideEffect(WelcomeSideEffect.ShowSnackBar(DataError.Remote.NO_INTERNET))

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
                    dispatch(OnQueryIsEmptyClearResults)
                }
            }
            .launchIn(storeScope)
    }

    private fun observeLocationChanges() {
        state
            .map { it.latitude to it.longitude }
            .distinctUntilChanged()
            .filter { (lat, lon) -> lat != 0.0 && lon != 0.0 }
            .onEach {
                geonamesJob?.cancel()
                geonamesJob = fetchReversGeocoding()
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
                emitSideEffect(WelcomeSideEffect.ShowSnackBar(error))
            }
            .onFinally {
                dispatch(OnLoadingFinish)
            }
    }

    private fun getGeolocationFromGpsSensors() {
        if (isOffline.value) {
            emitSideEffect(WelcomeSideEffect.ShowSnackBar(DataError.Remote.NO_INTERNET))
            dispatch(OnLoadingFinish)
        } else {
            dispatch(OnLoading)
            weatherLocationManager.getGeolocationFromGpsSensors(
                onSuccessfullyLocationReceived = { lat, lon ->
                    dispatch(OnSuccessfullyUpdateGeolocationFromGpsSensors(lat, lon))
                },
                onError = { geoError: AppError ->
                    dispatch(OnErrorUpdateGeolocationFromGpsSensors)
                    emitSideEffect(WelcomeSideEffect.ShowSnackBar(geoError))
                    emitSideEffect(WelcomeSideEffect.OnShowSettingsDialog(geoError))
                }
            )
        }
    }

    private fun fetchReversGeocoding() = storeScope.launch {
        dispatch(OnLoading)
        masterRepository.getReverseGeocodingLocation(
            latitude = state.value.latitude,
            longitude = state.value.longitude,
            language = "ru"
        ).onSuccess { searchResults ->
            dispatch(
                OnSuccessFetchReversGeocodingFromApi(
                    city = searchResults.geonames.firstOrNull()?.name.orEmpty(),
                    country = searchResults.geonames.firstOrNull()?.countryName.orEmpty(),
                    geoItemId = searchResults.geonames.firstOrNull()?.geoItemId ?: 0
                )
            )
        }.onError { apiError: DataError.Remote ->
            dispatch(OnErrorFetchReversGeocodingFromApi)
            emitSideEffect(WelcomeSideEffect.ShowSnackBar(apiError))
        }
    }

    private fun saveUserLocationToDataStore() {
        if (isOffline.value) {
            emitSideEffect(WelcomeSideEffect.ShowSnackBar(DataError.Remote.NO_INTERNET))
        } else {
            dispatch(OnLoading)
            storeScope.launch {
                dataStore.saveUserLocation(state.value.latitude, state.value.longitude)
                    .asEmptyDataResult()
                    .onSuccess {
                        dataStore.saveInitialLocationPicked(true).asEmptyDataResult()
                            .onError { prefError ->
                                emitSideEffect(WelcomeSideEffect.ShowSnackBar(prefError))
                            }
                    }
                    .onError { prefError ->
                        emitSideEffect(WelcomeSideEffect.ShowSnackBar(prefError))
                    }.onFinally {
                        dispatch(OnLoadingFinish)
                    }
            }
        }
    }

    override fun clear() {
        storeScope.cancel()
    }

    companion object {
        private const val THRESHOLD = 500L
    }
}
