package io.wookoo.weekly.mvi

import io.wookoo.models.ui.MainWeatherUiModel
import io.wookoo.models.ui.UiCalendarDayModel
import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI

sealed interface WeeklyIntent

data object OnLoading : WeeklyIntent
data object OnLoadingFinish : WeeklyIntent
data class OnCalendarItemClick(val indexPosition: Int) : WeeklyIntent

data class OnSetCalendar(val calendarItems: List<UiCalendarDayModel>) : WeeklyIntent
data class OnSetWeeklyForecast(
    val items: MainWeatherUiModel
) : WeeklyIntent

data class OnLoadWeeklyResponse(val weatherResponse: WeeklyWeatherDomainUI) : WeeklyIntent
data class OnSetCityName(val cityName: String) : WeeklyIntent
