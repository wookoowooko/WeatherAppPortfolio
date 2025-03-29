package io.wookoo.weekly.mvi

import io.wookoo.common.mvi.Reducer
import io.wookoo.models.ui.UiCalendarDayModel
import javax.inject.Inject

class WeeklyReducer @Inject constructor() : Reducer<WeeklyState, WeeklyIntent> {
    override fun reduce(
        state: WeeklyState,
        intent: WeeklyIntent,
    ): WeeklyState {
        return when (intent) {
            OnLoading -> state.copy(isLoading = true)
            OnLoadingFinish -> state.copy(isLoading = false)
            is OnCalendarItemClick -> {
                state.copy(
                    selectedCalendarItemIndex = intent.indexPosition,
                    weeklyCalendar = updateCalendarSelection(
                        state.weeklyCalendar,
                        intent.indexPosition
                    )
                )
            }

            is OnSetCalendar -> state.copy(
                weeklyCalendar = initializeCalendarSelection(intent.calendarItems),
                selectedCalendarItemIndex = 0
            )

            is OnLoadWeeklyResponse -> state.copy(weatherResponse = intent.weatherResponse)
            is OnSetWeeklyForecast -> state.copy(mainWeatherRecyclerItems = intent.items)
        }
    }

    private fun updateCalendarSelection(
        items: List<UiCalendarDayModel>,
        selectedIndex: Int,
    ) = items.mapIndexed { index, day ->
        day.copy(isSelected = index == selectedIndex)
    }

    private fun initializeCalendarSelection(
        items: List<UiCalendarDayModel>,
    ) = items.mapIndexed { index, day ->
        day.copy(isSelected = index == 0)
    }
}
