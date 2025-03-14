package io.wookoo.domain.model.weather.current

data class CurrentWeatherResponseModel(
    val cityName: String,
    val countryName: String,
    val geoNameId: String,
//    val latitude: Double,
//    val longitude: Double,
    val timezone: String,
    val current: CurrentDayModel,
    val hourly: HourlyModel,
    val daily: DailyModel,
)
