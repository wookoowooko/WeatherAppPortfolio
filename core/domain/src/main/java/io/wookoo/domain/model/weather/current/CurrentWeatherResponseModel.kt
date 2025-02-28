package io.wookoo.domain.model.weather.current

data class CurrentWeatherResponseModel(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentDayModel,
    val hourly: HourlyModel,
    val daily: DailyModel
)
