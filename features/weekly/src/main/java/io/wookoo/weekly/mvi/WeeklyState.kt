package io.wookoo.weekly.mvi

import io.wookoo.models.ui.MainWeatherUiModel
import io.wookoo.models.ui.UiCalendarDayModel
import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI

data class WeeklyState(
    val cityName: String = "",
    val isLoading: Boolean = true,
    val selectedCalendarItemIndex: Int = 0,
    val weeklyCalendar: List<UiCalendarDayModel> = emptyList(),
    val weatherResponse: WeeklyWeatherDomainUI? = null,
    val mainWeatherRecyclerItems: MainWeatherUiModel = MainWeatherUiModel(),
)
