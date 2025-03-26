package io.wookoo.welcome.mvi

import android.util.Log
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
import kotlinx.coroutines.flow.first
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
    }

    override fun handleSideEffects(intent: WelcomePageIntent) {
        when (intent) {
            is OnSearchGeoLocationClick -> storeScope.launch { getGeolocationFromGpsSensors() }
            is OnContinueButtonClick -> storeScope.launch { saveUserOnboardingDone() }
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

    // Functions
    private fun searchLocationFromApi(query: String) = storeScope.launch {
        dispatch(OnLoading)
        masterRepository.searchLocationFromApiByQuery(query, language = "ru")
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

//    private fun getGeolocationFromGpsSensors() {
//        if (isOffline.value) {
//            emitSideEffect(WelcomeSideEffect.ShowSnackBar(DataError.Remote.NO_INTERNET))
//            dispatch(OnLoadingFinish)
//        } else {
//            dispatch(OnLoading)
//            weatherLocationManager.getGeolocationFromGpsSensors(
//                onSuccessfullyLocationReceived = { lat, lon ->
//                    fetchReversGeocoding(
//                        latitude = lat,
//                        longitude = lon
//                    )
//                },
//                onError = { geoError: AppError ->
//                    dispatch(OnErrorUpdateGeolocationFromGpsSensors)
//                    emitSideEffect(WelcomeSideEffect.ShowSnackBar(geoError))
//                    emitSideEffect(WelcomeSideEffect.OnShowSettingsDialog(geoError))
//                }
//            )
//        }
//    }

    private suspend fun getGeolocationFromGpsSensors() {
        if (isOffline.value) {
            emitSideEffect(WelcomeSideEffect.ShowSnackBar(DataError.Remote.NO_INTERNET))
            dispatch(OnLoadingFinish)
        } else {
            dispatch(OnLoading)
            weatherLocationManager.getGeolocationFromGpsSensors().first()
                .onSuccess { geoLocation ->
                    Log.d(TAG, "geoLocation: $geoLocation")
                    fetchReversGeocoding(
                        latitude = geoLocation.first,
                        longitude = geoLocation.second
                    )
                }.onError { geoError: AppError ->
                    dispatch(OnErrorUpdateGeolocationFromGpsSensors)
                    emitSideEffect(WelcomeSideEffect.ShowSnackBar(geoError))
                    emitSideEffect(WelcomeSideEffect.OnShowSettingsDialog(geoError))

//            weatherLocationManager.getGeolocationFromGpsSensors().collect {
//                it.onSuccess { geoLocation ->
//                    Log.d(TAG, "geoLocation: $geoLocation")
//                    fetchReversGeocoding(
//                        latitude = geoLocation.first,
//                        longitude = geoLocation.second
//                    )
//                }.onError { geoError: AppError ->
//                    dispatch(OnErrorUpdateGeolocationFromGpsSensors)
//                    emitSideEffect(WelcomeSideEffect.ShowSnackBar(geoError))
//                    emitSideEffect(WelcomeSideEffect.OnShowSettingsDialog(geoError))
//                }
                }
        }
    }

    private fun fetchReversGeocoding(
        latitude: Double,
        longitude: Double,
    ) = storeScope.launch {
        masterRepository.getReverseGeocodingLocation(latitude, longitude, "ru")
            .onSuccess { gpsItems ->
                gpsItems.results.firstOrNull()?.let { geoName ->
                    dispatch(OnSuccessFetchReversGeocodingFromApi(geoName))
                } ?: run {
                    dispatch(OnErrorFetchReversGeocodingFromApi)
                    emitSideEffect(WelcomeSideEffect.ShowSnackBar(DataError.Remote.UNKNOWN))
                }
            }
            .onError { apiError ->
                dispatch(OnErrorFetchReversGeocodingFromApi)
                emitSideEffect(WelcomeSideEffect.ShowSnackBar(apiError))
            }
    }

//    private suspend fun saveUserOnboardingDone() {
//        dispatch(OnLoading)
//        if (isOffline.value) {
//            emitSideEffect(WelcomeSideEffect.ShowSnackBar(DataError.Remote.NO_INTERNET))
//        } else {
//            Log.d(TAG, "startSynchronize:Onboarding")
//            state.value.geoItem?.geoItemId?.let { id ->
//                masterRepository.synchronizeCurrentWeather(
//                    geoItemId = id
//                ).onSuccess {
//                    masterRepository.updateCurrentLocation(id)
//                        .onSuccess {
//                            dataStore.saveInitialLocationPicked(true)
//                                .asEmptyDataResult()
//                                .onError { prefError ->
//                                    emitSideEffect(WelcomeSideEffect.ShowSnackBar(prefError))
//                                }
//                        }
//                        .onError { dataBaseError ->
//                            emitSideEffect(WelcomeSideEffect.ShowSnackBar(dataBaseError))
//                        }
//                }.onError { dataBaseError ->
//                    emitSideEffect(WelcomeSideEffect.ShowSnackBar(dataBaseError))
//                }
//            }
//        }
//    }

    private suspend fun saveUserOnboardingDone() {
        dispatch(OnLoading)
        if (isOffline.value) {
            emitSideEffect(WelcomeSideEffect.ShowSnackBar(DataError.Remote.NO_INTERNET))
        } else {
            emitSideEffect(
                WelcomeSideEffect.OnSyncRequest(
                    geoItemId = state.value.geoItem?.geoItemId ?: -1,
                    isNeedToUpdate = true
                )
            )
            dataStore.saveInitialLocationPicked(true)
                .asEmptyDataResult()
                .onSuccess {
                    emitSideEffect(WelcomeSideEffect.OnNavigateToMain)
                }
                .onError { prefError ->
                    emitSideEffect(WelcomeSideEffect.ShowSnackBar(prefError))
                }.onFinally {
                    dispatch(OnLoadingFinish)
                }
        }
    }

    override fun clear() {
        Log.d(TAG, "cleared")
        storeScope.cancel()
    }

    companion object {
        private const val THRESHOLD = 500L
        private const val TAG = "WelcomePageStore"
    }
}
