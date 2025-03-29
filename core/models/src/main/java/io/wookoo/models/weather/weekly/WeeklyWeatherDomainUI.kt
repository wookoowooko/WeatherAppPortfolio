package io.wookoo.models.weather.weekly

import io.wookoo.models.weather.weekly.additional.WeeklyWeatherModel

data class WeeklyWeatherDomainUI(
    val isDay: Boolean,
    val weekly: io.wookoo.models.weather.weekly.additional.WeeklyWeatherModel,
    val utcOffsetSeconds: Long
)
