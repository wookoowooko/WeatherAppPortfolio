package io.wookoo.weekly.uimappers

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.domain.units.WeatherUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.weekly.uimodels.UIWindCardModel

fun WeeklyWeatherDomainUI.asUiWindCardModel(
    selectedCalendarItemIndex: Int,
    formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
): UIWindCardModel {
    return UIWindCardModel(
        windSpeed = WeatherValueWithUnit(
            this@asUiWindCardModel.weekly.windData[selectedCalendarItemIndex].speed,
            WeatherUnit.KMH
        ),

        windGust = WeatherValueWithUnit(
            this@asUiWindCardModel.weekly.windData[selectedCalendarItemIndex].gust,
            WeatherUnit.KMH
        ),
        windDirection = formatWindDirectionUseCase(
            this@asUiWindCardModel.weekly.windData[selectedCalendarItemIndex].direction
        ),
    )
}
