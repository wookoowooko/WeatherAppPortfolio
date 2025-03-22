package io.wookoo.cities.uimodels

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.units.WeatherValueWithUnit

data class UiCity(
    val date: String,
    val geoItemId: Long,
    val isDay: Boolean,
    val cityName: String,
    val countryName: String,
    val temperature: WeatherValueWithUnit = WeatherValueWithUnit(),
    val temperatureFeelsLike: WeatherValueWithUnit = WeatherValueWithUnit(),
    val weatherStatus: WeatherCondition = WeatherCondition.UNKNOWN,
    val minTemperature: WeatherValueWithUnit = WeatherValueWithUnit(),
    val maxTemperature: WeatherValueWithUnit = WeatherValueWithUnit()
)
