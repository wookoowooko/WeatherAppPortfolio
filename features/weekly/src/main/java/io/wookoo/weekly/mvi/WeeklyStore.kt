package io.wookoo.weekly.mvi

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.onError
import io.wookoo.weekly.navigation.WeeklyRoute
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeeklyStore @Inject constructor(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    private val masterRepository: IMasterWeatherRepo,
    savedStateHandle: SavedStateHandle,
//    dataStore: IDataStoreRepo,
    reducer: WeeklyReducer,
) : Store<WeeklyState, WeeklyIntent, WeeklyEffect>(
    reducer = reducer, storeScope = storeScope, initialState = WeeklyState()
) {
//    private val settings = dataStore.userSettings.stateIn(
//        storeScope, SharingStarted.WhileSubscribed(5000L), initialValue = UserSettingsModel()
//    )

    private val latitude = savedStateHandle.toRoute<WeeklyRoute>().latitude
    private val longitude = savedStateHandle.toRoute<WeeklyRoute>().longitude
    private val geoItemId = savedStateHandle.toRoute<WeeklyRoute>().geoItemId


    init {
        Log.d(TAG, "latitude: $latitude")
        Log.d(TAG, "longitude: $longitude")
        Log.d(TAG, "geoItemId: $geoItemId")
    }


    override fun initializeObservers() {
        Log.d(TAG, "initializeObservers: ")
        observeSelectedDayPositionChanged()
//        observeWeeklyWeatherFromApi()
        observeWeeklyWeather()
        synchronizeWeatherOnStart()
    }


    private fun observeSelectedDayPositionChanged() {
        Log.d(TAG, "observeSelectedDayPositionChanged")
        state.map { it.selectedCalendarItemIndex }
            .distinctUntilChanged()
            .onEach { selectedIndex ->
                Log.d(TAG, "selectedIndex: $selectedIndex ")
                dispatch(OnObserveSelectedDayPositionChanged(selectedIndex))
                Log.d(TAG, "updated ")
            }
            .launchIn(storeScope)
    }

    //
    private fun observeWeeklyWeather() {
        Log.d(TAG, "observeWeeklyWeather started")
        masterRepository.weeklyWeatherForecastFlowFromDB(geoItemId)
            .distinctUntilChanged()
            .onEach { weeklyWeather: WeeklyWeatherResponseModel ->
                Log.d(TAG, "observeWeeklyWeather: $weeklyWeather")
                dispatch(OnObserveWeeklyForecast(weeklyWeather))
            }.launchIn(storeScope)
    }

    private fun synchronizeWeatherOnStart() = storeScope.launch {
        if (latitude != 0.0 && longitude != 0.0) {
            masterRepository.syncWeeklyWeatherFromAPIAndSaveToCache(
                latitude = latitude, longitude = longitude, geoItemId = geoItemId
            ).onError { syncError ->
                emitSideEffect(WeeklyEffect.OnShowSnackBar(syncError))
            }
        }
    }


//    private fun synchronizeWeatherOnStart() {
//        Log.d(TAG, "synchronizeWeatherOnStart: ")
//        settings
//            .mapNotNull { setting ->
//                setting.location.takeIf { it.latitude != 0.0 && it.longitude != 0.0 }
//            }
//            .distinctUntilChanged()
//            .onEach { location ->
//                Log.d(TAG, "location: $location")
//                synchronizeWeather(location.latitude, location.longitude)
//            }
//            .launchIn(storeScope)
//    }


//        .onSuccess {
//            Log.d(TAG, "Successfully synchronized weather")
//            masterRepository.fetchWeeklyWeatherForecastFromAPI(latitude, longitude)
//                .onSuccess { weeklyWeather ->
//                    Log.d(TAG, "Successfully fetched weekly weather: $weeklyWeather")
//                    dispatch(OnObserveWeeklyForecast(weeklyWeather))
//                }
//                .onError { apiError ->
//                    Log.d(TAG, "Failed to fetch weekly weather: $apiError")
//                    emitSideEffect(WeeklyEffect.OnShowSnackBar(apiError))
//                }
//        }


//    private fun synchronizeWeather(latitude: Double, longitude: Double) {
//        storeScope.launch {
//            masterRepository.syncWeeklyWeather(
//                latitude = latitude,
//                longitude = longitude
//            ).onError { apiError ->
//                Log.d(TAG, "synchronizeWeather: $apiError")
//                emitSideEffect(WeeklyEffect.OnShowSnackBar(apiError))
//            }
//        }
//    }

//    private fun observeWeeklyWeatherFromApi() {
//        dispatch(OnLoading)
//        Log.d(TAG, "observeWeeklyWeatherFromApi: ")
//        settings.map { settings ->
//            Log.d(TAG, "setting: $settings")
//            settings.location
//        }.map { location ->
//            Log.d(TAG, "location: $location")
//            location.latitude to location.longitude
//        }.distinctUntilChanged()
//            .filter { (lat, lon) -> lat != 0.0 && lon != 0.0 }
//            .onEach { (lat, lon) ->
//                masterRepository.getWeeklyWeather(lat, lon)
//                    .onSuccess { forecast: WeeklyWeatherResponseModel ->
//                        dispatch(OnObserveWeeklyForecast(forecast))
//                    }
//                    .onError { apiError ->
//                        emitSideEffect(WeeklyEffect.OnShowSnackBar(apiError))
//                    }
//                    .onFinally {
//                        dispatch(OnLoadingFinish)
//                    }
//            }.launchIn(storeScope)
//    }

    companion object {
        private const val TAG = "WeeklyStore"
    }
}
