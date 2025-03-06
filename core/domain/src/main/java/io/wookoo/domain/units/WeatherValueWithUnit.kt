package io.wookoo.domain.units

data class WeatherValueWithUnit(val value: Number, val unit: ApiUnit? = null) :
    ApplicationUnit
