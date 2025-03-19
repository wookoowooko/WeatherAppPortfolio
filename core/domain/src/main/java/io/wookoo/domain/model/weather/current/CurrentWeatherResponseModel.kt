package io.wookoo.domain.model.weather.current

data class CurrentWeatherResponseModel(
    val cityName: String,
    val countryName: String,
    val geoNameId: Long,
    val latitude: Double,
    val longitude: Double,
    val current: CurrentDayModel,
    val hourly: HourlyModel,
    val daily: DailyModel,
)
