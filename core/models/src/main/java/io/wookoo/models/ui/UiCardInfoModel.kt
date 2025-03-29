package io.wookoo.models.ui

import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherValueWithUnit

data class UiCardInfoModel(
    val tempMax: WeatherValueWithUnit,
    val tempMin: String,
    val feelsLikeMin: WeatherValueWithUnit,
    val feelsLikeMax: WeatherValueWithUnit,
    val weatherCondition: WeatherCondition = WeatherCondition.UNKNOWN,
    val isDay: Boolean,
) : DisplayableItem {
    override fun id(): Any = tempMax

    override fun content(): Any = Content(
        tempMin,
        feelsLikeMin,
        feelsLikeMax,
        weatherCondition,
        isDay
    )

    data class Content(
        val tempMin: String,
        val feelsLikeMin: WeatherValueWithUnit,
        val feelsLikeMax: WeatherValueWithUnit,
        val weatherCondition: WeatherCondition = WeatherCondition.UNKNOWN,
        val isDay: Boolean,
    )
}
