package io.wookoo.weekly.mvi

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.utils.AppError

sealed interface WeeklyIntent

data object OnLoading : WeeklyIntent
data object OnLoadingFinish : WeeklyIntent
data class OnCalendarItemClick(val indexPosition: Int) : WeeklyIntent
data class OnObserveSelectedDayPositionChanged(val selectedIndex: Int) : WeeklyIntent
data class OnObserveWeeklyForecast(val forecast: WeeklyWeatherResponseModel) : WeeklyIntent

sealed interface WeeklyEffect {
    data class OnShowSnackBar(val error: AppError) : WeeklyEffect
}
