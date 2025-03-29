package io.wookoo.domain.model.weather

import io.wookoo.domain.units.WeatherUnit

data class WeatherDataUnit(
    val apiValue: String,
    val stringValue: WeatherUnit,
)
