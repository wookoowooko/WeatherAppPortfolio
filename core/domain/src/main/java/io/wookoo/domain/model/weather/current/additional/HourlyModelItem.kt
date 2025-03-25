package io.wookoo.domain.model.weather.current.additional

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.units.WeatherValueWithUnit

data class HourlyModelItem(
    val time: String,
    val temperature: WeatherValueWithUnit,
    val weatherCode: WeatherCondition,
    val isNow: Boolean,
    val isDay: Boolean
)
