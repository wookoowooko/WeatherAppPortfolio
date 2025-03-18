package io.wookoo.weekly.mvi

import android.util.Log
import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.settings.UserSettingsModel
import io.wookoo.domain.utils.onError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeeklyStore @Inject constructor(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    private val masterRepository: IMasterWeatherRepo,

    dataStore: IDataStoreRepo,
    reducer: WeeklyReducer,
) : Store<WeeklyState, WeeklyIntent, WeeklyEffect>(
    reducer = reducer,
    storeScope = storeScope,
    initialState = WeeklyState()
) {
    private val settings = dataStore.userSettings.stateIn(
        storeScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = UserSettingsModel()
    )

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
            .onEach { selectedIndex ->
                Log.d(TAG, "selectedIndex: $selectedIndex ")
                dispatch(OnObserveSelectedDayPositionChanged(selectedIndex))
            }
            .launchIn(storeScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeWeeklyWeather() {
        settings
            .map { it.lastGeoName }
            .flatMapLatest { lastGeoName ->
                masterRepository.weeklyWeather(lastGeoName)
                    .onEach { weeklyWeather ->
                        dispatch(OnObserveWeeklyForecast(weeklyWeather))
                    }
            }
            .launchIn(storeScope)
    }

    private fun synchronizeWeatherOnStart() {
        settings
            .mapNotNull { setting ->
                setting.location.takeIf { it.latitude != 0.0 && it.longitude != 0.0 }
            }
            .distinctUntilChanged()
            .onEach { location ->
                synchronizeWeather(location.latitude, location.longitude)
            }
            .launchIn(storeScope)
    }

    private fun synchronizeWeather(latitude: Double, longitude: Double) {
        storeScope.launch {
            masterRepository.syncWeeklyWeather(
                latitude = latitude,
                longitude = longitude
            ).onError { apiError ->
                Log.d(TAG, "synchronizeWeather: $apiError")
                emitSideEffect(WeeklyEffect.OnShowSnackBar(apiError))
            }
        }
    }

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
