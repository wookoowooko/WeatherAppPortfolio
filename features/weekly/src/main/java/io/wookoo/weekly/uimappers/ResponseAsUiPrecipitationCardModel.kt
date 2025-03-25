package io.wookoo.weekly.uimappers

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.domain.units.WeatherUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.weekly.uimodels.UIPrecipitationCardModel

fun WeeklyWeatherDomainUI.asUiPrecipitationCardModel(
    selectedCalendarItemIndex: Int,
): UIPrecipitationCardModel {
    return UIPrecipitationCardModel(
        total = WeatherValueWithUnit(
            this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].level,
            WeatherUnit.MM
        ),
        rainSum = WeatherValueWithUnit(
            this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].rain,
            WeatherUnit.MM
        ),
        showersSum =
        WeatherValueWithUnit(
            this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].showers,
            WeatherUnit.MM
        ),
        snowSum = WeatherValueWithUnit(
            this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].snowfall,
            WeatherUnit.CM
        ),
        precipitationProbability = WeatherValueWithUnit(
            this@asUiPrecipitationCardModel.weekly.precipitationProbabilityMax[selectedCalendarItemIndex],
            WeatherUnit.PERCENT
        )
    )
}
