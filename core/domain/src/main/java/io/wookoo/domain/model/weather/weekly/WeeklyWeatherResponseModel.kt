package io.wookoo.domain.model.weather.weekly

data class WeeklyWeatherResponseModel(
    val currentShort: CurrentWeatherShortModel,
    val weekly: WeeklyWeatherModel,
)
