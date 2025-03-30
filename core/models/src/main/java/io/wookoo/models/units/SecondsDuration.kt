package io.wookoo.models.units

data class SecondsDuration(
    val hour: WeatherValueWithUnit,
    val minute: WeatherValueWithUnit
) : ApplicationUnit
