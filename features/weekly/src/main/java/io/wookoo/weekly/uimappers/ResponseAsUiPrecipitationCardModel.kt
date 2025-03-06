package io.wookoo.weekly.uimappers

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.units.ApiUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.weekly.uimodels.UIPrecipitationCardModel

fun WeeklyWeatherResponseModel.asUiPrecipitationCardModel(
    selectedCalendarItemIndex: Int,
): UIPrecipitationCardModel {
    return UIPrecipitationCardModel(

        total = WeatherValueWithUnit(
            this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].level,
            ApiUnit.MM
        ),
        rainSum = WeatherValueWithUnit(
            this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].rain,
            ApiUnit.MM
        ),
        showersSum =
        WeatherValueWithUnit(
            this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].showers,
            ApiUnit.MM
        ),
        snowSum = WeatherValueWithUnit(
            this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].snowfall,
            ApiUnit.CM
        ),
        precipitationProbability = WeatherValueWithUnit(
            this@asUiPrecipitationCardModel.weekly.precipitationProbabilityMax[selectedCalendarItemIndex],
            ApiUnit.PERCENT
        )
    )
}