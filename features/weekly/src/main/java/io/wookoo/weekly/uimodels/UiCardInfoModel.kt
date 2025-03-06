package io.wookoo.weekly.uimodels

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.weekly.DisplayableItem

data class UiCardInfoModel(
    val tempMax: WeatherValueWithUnit,
    val tempMin: WeatherValueWithUnit,
    val feelsLikeMin: WeatherValueWithUnit,
    val feelsLikeMax: WeatherValueWithUnit,
    val weatherCondition: WeatherCondition = WeatherCondition.UNKNOWN,
    val isDay: Boolean,
) : DisplayableItem {
    override fun id(): Any {
        return tempMax
    }

    override fun content(): Any = Content(
        tempMin,
        feelsLikeMin,
        feelsLikeMax,
        weatherCondition,
        isDay
    )

     data class Content(
        val tempMin: WeatherValueWithUnit,
        val feelsLikeMin: WeatherValueWithUnit,
        val feelsLikeMax: WeatherValueWithUnit,
        val weatherCondition: WeatherCondition = WeatherCondition.UNKNOWN,
        val isDay: Boolean,
    )
}
