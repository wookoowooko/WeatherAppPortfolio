package io.wookoo.weekly.mvi

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.enums.UpdateIntent
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
    reducer: WeeklyReducer,
) : Store<WeeklyState, WeeklyIntent, WeeklyEffect>(
    reducer = reducer,
    storeScope = storeScope,
    initialState = WeeklyState()
) {

    private val geoItemId: Long = savedStateHandle.toRoute<WeeklyRoute>().geoItemId

    override fun initializeObservers() {
        Log.d(TAG, "initializeObservers: ")
        observeSelectedDayPositionChanged()
        observeWeeklyWeather()
        syncWeeklyWeatherFromAPIAndSaveToCache()
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

    private fun observeWeeklyWeather() {
        Log.d(TAG, "observeWeeklyWeather started")
        masterRepository.weeklyWeatherForecastFlowFromDB(geoItemId)
            .distinctUntilChanged()
            .onEach { weeklyWeather: WeeklyWeatherResponseModel ->
                Log.d(TAG, "observeWeeklyWeather: $weeklyWeather")
                dispatch(OnObserveWeeklyForecast(weeklyWeather))
            }.launchIn(storeScope)
    }

    private fun syncWeeklyWeatherFromAPIAndSaveToCache() = storeScope.launch {
        masterRepository.syncWeeklyWeatherFromAPIAndSaveToCache(
            geoItemId = geoItemId,
            updateIntent = UpdateIntent.FROM_USER
        ).onError { syncError ->
            emitSideEffect(WeeklyEffect.OnShowSnackBar(syncError))
        }
    }

    companion object {
        private const val TAG = "WeeklyStore"
    }
}
