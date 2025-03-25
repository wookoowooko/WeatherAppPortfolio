package io.wookoo.weekly.mvi

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.weekly.uimodels.MainWeatherUiModel
import io.wookoo.weekly.uimodels.UiCalendarDayModel
import io.wookoo.weekly.uimodels.UiCardInfoModel

data class WeeklyState(
    val cityName: String = "",
    val isLoading: Boolean = true,
    val selectedCalendarItemIndex: Int = 0,
    val weeklyCalendar: List<UiCalendarDayModel> = emptyList(),
    val cardInfo: UiCardInfoModel? = null,
    val weatherResponse: WeeklyWeatherDomainUI? = null,
    val mainWeatherRecyclerItems: MainWeatherUiModel = MainWeatherUiModel(),
)
