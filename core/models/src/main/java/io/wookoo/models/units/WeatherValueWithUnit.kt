package io.wookoo.models.units

data class WeatherValueWithUnit(
    val value: Number = 0,
    val unit: WeatherUnit? = null
) : ApplicationUnit
