package io.wookoo.weekly.uimappers

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.units.ApiUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.weekly.uimodels.UIWindCardModel

fun WeeklyWeatherResponseModel.asUiWindCardModel(
    selectedCalendarItemIndex: Int,
    formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
): UIWindCardModel {
    return UIWindCardModel(
        windSpeed = WeatherValueWithUnit(
            this@asUiWindCardModel.weekly.windData[selectedCalendarItemIndex].speed,
            ApiUnit.KMH
        ),

        windGust = WeatherValueWithUnit(
            this@asUiWindCardModel.weekly.windData[selectedCalendarItemIndex].gust,
            ApiUnit.KMH
        ),
        windDirection = formatWindDirectionUseCase(
            this@asUiWindCardModel.weekly.windData[selectedCalendarItemIndex].direction
        ),
    )
}
