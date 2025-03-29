package io.wookoo.models.weather.current.additional

import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherValueWithUnit

data class HourlyModelItem(
    val time: String,
    val temperature: WeatherValueWithUnit,
    val weatherCode: WeatherCondition,
    val isNow: Boolean,
    val isDay: Boolean
)
