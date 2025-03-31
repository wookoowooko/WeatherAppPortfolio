package io.wookoo.models.ui

import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherValueWithUnit

data class UiCity(
    val isCurrentLocation: Boolean,
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
