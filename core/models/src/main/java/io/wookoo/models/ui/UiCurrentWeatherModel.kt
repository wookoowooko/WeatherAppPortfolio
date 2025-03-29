package io.wookoo.models.ui

import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.models.units.WindDirection
import io.wookoo.models.weather.current.additional.HourlyModelItem

data class UiCurrentWeatherModel(
    val city: String = "",
    val country: String = "",
    val geoNameId: Long = 0L,
    val date: String = "",
    val isDay: Boolean = true,
    val humidity: WeatherValueWithUnit = WeatherValueWithUnit(),
    val windSpeed: WeatherValueWithUnit = WeatherValueWithUnit(),
    val windDirection: WindDirection = WindDirection.UNDETECTED,
    val windGust: WeatherValueWithUnit = WeatherValueWithUnit(),
    val precipitation: WeatherValueWithUnit = WeatherValueWithUnit(),
    val pressureMsl: WeatherValueWithUnit = WeatherValueWithUnit(),
    val temperature: WeatherValueWithUnit = WeatherValueWithUnit(),
    val temperatureFeelsLike: WeatherValueWithUnit = WeatherValueWithUnit(),
    val weatherStatus: WeatherCondition = WeatherCondition.UNKNOWN,
    val hourlyList: List<HourlyModelItem> = emptyList(),
    val sunriseTime: String = "",
    val sunsetTime: String = "",
    val uvIndex: String = "",
)
