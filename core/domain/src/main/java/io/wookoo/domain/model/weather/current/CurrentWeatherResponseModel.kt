package io.wookoo.domain.model.weather.current

data class CurrentWeatherResponseModel(
    val time: Long,
    val cityName: String,
    val countryName: String,
    val geoItemId: Long,
    val current: CurrentDayModel,
    val hourly: HourlyModel,
    val daily: DailyModel,
    val utcOffsetSeconds: Long,
)
