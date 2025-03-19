package io.wookoo.domain.model.weather.weekly

data class WeeklyWeatherResponseModel(
    val latitude: Double,
    val longitude: Double,
    val currentShort: CurrentWeatherShortModel,
    val weekly: WeeklyWeatherModel,
)
