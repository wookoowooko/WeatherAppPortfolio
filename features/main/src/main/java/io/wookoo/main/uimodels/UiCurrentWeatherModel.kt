package io.wookoo.main.uimodels

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.model.weather.current.HourlyModelItem
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.units.WindDirection

data class UiCurrentWeatherModel(
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
