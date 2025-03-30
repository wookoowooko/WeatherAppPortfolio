package io.wookoo.models.units

data class WeatherUnits(
    val temperature: WeatherUnit,
    val precipitation: WeatherUnit,
    val windSpeed: WeatherUnit
)
