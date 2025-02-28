package io.wookoo.domain.model.weather.current

import io.wookoo.domain.enums.WeatherCondition

data class HourlyModelItem(
    val time: String,
    val temperature: String,
    val weatherCode: WeatherCondition,
    val isNow: Boolean,
    val isDay: Boolean
)
