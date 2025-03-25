package io.wookoo.domain.model.weather.weekly

import io.wookoo.domain.model.weather.weekly.additional.WeeklyWeatherModel

data class WeeklyWeatherDomainUI(
    val isDay: Boolean,
    val weekly: WeeklyWeatherModel,
    val utcOffsetSeconds: Long
)
