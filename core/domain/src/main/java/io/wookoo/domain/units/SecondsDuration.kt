package io.wookoo.domain.units

data class SecondsDuration(
    val hour: WeatherValueWithUnit,
    val minute: WeatherValueWithUnit
) : ApplicationUnit
