package io.wookoo.cities.uimodels

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.units.WeatherValueWithUnit

data class UiCity(
    val isDay: Boolean,
    val cityName: String,
    val countryName: String,
    val temperature: WeatherValueWithUnit = WeatherValueWithUnit(),
    val temperatureFeelsLike: WeatherValueWithUnit = WeatherValueWithUnit(),
    val weatherStatus: WeatherCondition = WeatherCondition.UNKNOWN,
)
