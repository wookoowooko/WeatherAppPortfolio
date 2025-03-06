package io.wookoo.weekly.uimappers

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.units.ApiUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.weekly.uimodels.UIWindCardModel
import io.wookoo.weekly.uimodels.UiCardInfoModel

fun WeeklyWeatherResponseModel.asUiCardInfoModel(
    selectedCalendarItemIndex: Int,
    convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
): UiCardInfoModel {
    return UiCardInfoModel(
        tempMax = WeatherValueWithUnit(
            this.weekly.tempMax[selectedCalendarItemIndex],
            ApiUnit.CELSIUS
        ),
        tempMin = WeatherValueWithUnit(
            this.weekly.tempMin[selectedCalendarItemIndex]
        ),
        feelsLikeMax = WeatherValueWithUnit(
            this.weekly.apparentTempMax[selectedCalendarItemIndex],
            ApiUnit.CELSIUS
        ),
        feelsLikeMin = WeatherValueWithUnit(
            this.weekly.apparentTempMin[selectedCalendarItemIndex],
            ApiUnit.CELSIUS
        ),
        weatherCondition = convertWeatherCodeToEnumUseCase(this.weekly.weatherCode[selectedCalendarItemIndex]),
        isDay = this.currentShort.isDay
    )
}



