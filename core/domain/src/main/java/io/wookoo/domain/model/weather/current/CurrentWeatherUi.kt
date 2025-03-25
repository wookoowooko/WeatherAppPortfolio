package io.wookoo.domain.model.weather.current

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.model.weather.current.additional.HourlyModelItem

data class CurrentWeatherUi(
    val geoNameId: Long = 0L,
    val date: String = "",
    val isDay: Boolean = true,
    val humidity: String = "",
    val windSpeed: String = "",
    val windDirection: String = "",
    val windGust: String = "",
    val precipitation: String = "",
    val pressureMsl: String = "",
    val temperature: String = "",
    val temperatureFeelsLike: String = "",
    val weatherStatus: Pair<String, WeatherCondition> = Pair("", WeatherCondition.UNKNOWN),
    val hourlyList: List<HourlyModelItem> = emptyList(),
    val sunriseTime: String = "",
    val sunsetTime: String = "",
    val uvIndex: String = "",
)
