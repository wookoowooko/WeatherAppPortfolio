package io.wookoo.weekly.mvi

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.repo.IWeeklyForecastRepo
import io.wookoo.domain.usecases.MapWeeklyWeatherToCalendarUiUseCase
import io.wookoo.domain.usecases.MapWeeklyWeatherToUiUseCase
import io.wookoo.weekly.navigation.WeeklyRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class WeeklyStore @Inject constructor(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    private val weeklyForecast: IWeeklyForecastRepo,
    savedStateHandle: SavedStateHandle,
    reducer: WeeklyReducer,
    private val mapWeeklyWeatherToUiUseCase: MapWeeklyWeatherToUiUseCase,
    private val mapWeeklyWeatherToCalendarUiUseCase: MapWeeklyWeatherToCalendarUiUseCase,
) : Store<WeeklyState, WeeklyIntent, WeeklyEffect>(
    reducer = reducer,
    storeScope = storeScope,
    initialState = WeeklyState()
) {
    private val geoItemId: Long = savedStateHandle.toRoute<WeeklyRoute>().geoItemId

    override fun initializeObservers() {
        loadInitialData()
        setupIndexUpdates()
    }

    private fun loadInitialData() {
        weeklyForecast.getWeeklyForecastByGeoItemId(geoItemId)
            .onEach { response ->
                val calendarItems = mapWeeklyWeatherToCalendarUiUseCase(response)
                dispatch(OnSetCalendar(calendarItems))
                dispatch(OnSetCityName(response.weekly.cityName))
                dispatch(OnLoadWeeklyResponse(response))
            }
            .launchIn(storeScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun setupIndexUpdates() {
        state
            .map { it.selectedCalendarItemIndex to it.weatherResponse }
            .filter { (_, response) -> response != null }
            .flatMapLatest { (index, response) ->
                flow {
                    response?.let { weeklyForecast ->
                        emit(mapWeeklyWeatherToUiUseCase(weeklyForecast, index))
                    }
                }
            }
            .onEach { items ->
                dispatch(OnSetWeeklyForecast(items))
                dispatch(OnLoadingFinish)
            }
            .launchIn(storeScope)
    }
}
