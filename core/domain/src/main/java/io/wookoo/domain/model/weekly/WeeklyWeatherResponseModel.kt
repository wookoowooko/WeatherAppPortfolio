package io.wookoo.domain.model.weekly

data class WeeklyWeatherResponseModel(
    val currentShort: CurrentWeatherShortModel,
    val weekly: WeeklyWeatherModel,
)
