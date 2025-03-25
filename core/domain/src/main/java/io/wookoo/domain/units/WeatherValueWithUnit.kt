package io.wookoo.domain.units

data class WeatherValueWithUnit(
    val value: Number = 0,
    val unit: WeatherUnit? = null
) : ApplicationUnit
