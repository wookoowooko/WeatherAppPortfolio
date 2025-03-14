package io.wookoo.welcome.mvi

import android.util.Log
import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.model.reversegeocoding.ReverseGeocodingResponseModel
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.ILocationProvider
import io.wookoo.domain.repo.IMasterWeatherRepo
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class WelcomePageStore @Inject constructor(
    reducer: WelcomePageReducer,
    private val masterRepository: IMasterWeatherRepo,
    private val dataStore: IDataStoreRepo,
    private val weatherLocationManager: ILocationProvider,
    @StoreViewModelScope private val storeScope: CoroutineScope,
) : Store<WelcomePageState, WelcomePageIntent, WelcomeSideEffect>(
    initialState = WelcomePageState(),
    storeScope = storeScope,
    reducer = reducer,
) {
    private var geonamesJob: Job? = null
    private var searchJob: Job? = null

    override fun initializeObservers() {
        observeLocationChanges()
        observeSearchQuery()
    }

    override fun handleSideEffects(intent: WelcomePageIntent) {
        when (intent) {
            is OnSearchQueryChange -> searchLocationFromApi(intent.query)
            is OnSearchGeoLocationClick -> getGeolocationFromGpsSensors()
            is OnContinueButtonClick -> saveUserLocationToDataStore()
            else -> Unit
        }
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
                dispatch(OnSuccessSearchLocation(results = searchResults.results))
            }
            .onError { error ->
                dispatch(OnErrorSearchLocation)
                emitSideEffect(WelcomeSideEffect.ShowSnackBar(error))
            }
    }

    private fun getGeolocationFromGpsSensors() {
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

    private fun observeLocationChanges() {
        state
            .map { it.latitude to it.longitude }
            .distinctUntilChanged()
            .filter { (lat, lon) -> lat != 0.0 && lon != 0.0 }
            .onEach { (lat, lon) ->
                println("observeLocationChanges: Coordinates changed: lat=$lat, lon=$lon")
                geonamesJob?.cancel()
                geonamesJob = fetchReversGeocoding().also {
                    println("fetchReversGeocoding job started")
                }
            }
            .launchIn(storeScope)
    }

    private fun fetchReversGeocoding() = storeScope.launch {
        println("fetchReversGeocoding called")

        dispatch(OnLoading)

        masterRepository.getReverseGeocodingLocation(
            latitude = state.value.latitude,
            longitude = state.value.longitude,
            language = "ru"
        ).onSuccess { searchResults: ReverseGeocodingResponseModel ->

            Log.d(TAG, "fetchReversGeocoding: $searchResults")
            dispatch(
                OnSuccessFetchReversGeocodingFromApi(
                    city = searchResults.geonames.firstOrNull()?.name.orEmpty(),
                    country = searchResults.geonames.firstOrNull()?.countryName.orEmpty(),
                    geoItemId = searchResults.geonames.firstOrNull()?.geoItemId ?: 0
                )
            )
        }.onError { apiError: DataError.Remote ->
            println("fetchReversGeocoding failed: ${apiError.name}")
            dispatch(OnErrorFetchReversGeocodingFromApi)
            emitSideEffect(WelcomeSideEffect.ShowSnackBar(apiError))
        }
    }

    private fun saveUserLocationToDataStore() {
        dispatch(OnLoading)
        storeScope.launch {
            dataStore.saveUserLocation(state.value.latitude, state.value.longitude)
                .onSuccess {
                    Log.d(TAG, "geoId in Welcome: ${state.value.geoItemId}")
                    dataStore.saveGeoNameId(state.value.geoItemId)
                        .onError {
                            emitSideEffect(WelcomeSideEffect.ShowSnackBar(it))
                        }
                    dataStore.saveInitialLocationPicked(true)
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

    override fun clear() {
        println("clearedTasks")
        storeScope.cancel()
    }

    companion object {
        private const val TAG = "WelcomePageStore"
        private const val THRESHOLD = 500L
    }
}
