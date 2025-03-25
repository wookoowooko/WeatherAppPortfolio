package io.wookoo.domain.utils

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.units.WeatherUnit
import io.wookoo.domain.units.WindDirection

interface IStringProvider {
    fun getString(resId: Int): String
    fun fromApiUnit(unit: WeatherUnit): String
    fun fromWeatherCondition(condition: WeatherCondition, isDay: Boolean): String
    fun fromWindDirection(direction: WindDirection): String
}
