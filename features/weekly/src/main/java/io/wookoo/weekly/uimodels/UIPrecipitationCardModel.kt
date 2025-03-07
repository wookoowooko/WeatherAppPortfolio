package io.wookoo.weekly.uimodels

import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.weekly.DisplayableItem

data class UIPrecipitationCardModel(
    val total: WeatherValueWithUnit,
    val rainSum: WeatherValueWithUnit,
    val showersSum: WeatherValueWithUnit,
    val snowSum: WeatherValueWithUnit,
    val precipitationProbability: WeatherValueWithUnit,
) : DisplayableItem {
    override fun id(): Any = total

    override fun content(): Any = Content(
        rainSum,
        showersSum,
        snowSum,
        precipitationProbability
    )

    internal data class Content(
        val rainSum: WeatherValueWithUnit,
        val showersSum: WeatherValueWithUnit,
        val snowSum: WeatherValueWithUnit,
        val precipitationProbability: WeatherValueWithUnit,
    )
}
