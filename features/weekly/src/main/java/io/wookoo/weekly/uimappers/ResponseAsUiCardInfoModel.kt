package io.wookoo.weekly.uimappers

import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.weekly.uimodels.UiCardInfoModel

fun io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI.asUiCardInfoModel(
    selectedCalendarItemIndex: Int,
    convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
): UiCardInfoModel {
    return UiCardInfoModel(
        tempMax = WeatherValueWithUnit(
            this.weekly.tempMax[selectedCalendarItemIndex],
            WeatherUnit.CELSIUS
        ),
        tempMin = this.weekly.tempMin[selectedCalendarItemIndex].toInt().toString(),
        feelsLikeMax = WeatherValueWithUnit(
            this.weekly.apparentTempMax[selectedCalendarItemIndex],
            WeatherUnit.CELSIUS
        ),
        feelsLikeMin = WeatherValueWithUnit(
            this.weekly.apparentTempMin[selectedCalendarItemIndex],
            WeatherUnit.CELSIUS
        ),
        weatherCondition = convertWeatherCodeToEnumUseCase(this.weekly.weatherCode[selectedCalendarItemIndex]),
        isDay = this.isDay
    )
}
