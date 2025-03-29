package io.wookoo.weekly.uimappers

import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.weekly.uimodels.UiOtherPropsModel
import kotlin.math.roundToInt

fun io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI.asOtherPropsCardModel(
    selectedCalendarItemIndex: Int,
): UiOtherPropsModel {
    return UiOtherPropsModel(
        dayLightDuration = io.wookoo.models.units.SecondsDuration(
            hour = WeatherValueWithUnit(
                value = (this.weekly.dayLightDuration[selectedCalendarItemIndex] / 3600),
                unit = WeatherUnit.HOUR
            ),
            minute = WeatherValueWithUnit(
                value = (this.weekly.dayLightDuration[selectedCalendarItemIndex] % 3600) / 60,
                unit = WeatherUnit.MINUTE
            )
        ),
        sunShineDuration = io.wookoo.models.units.SecondsDuration(
            hour = WeatherValueWithUnit(
                value = (this.weekly.sunshineDuration[selectedCalendarItemIndex] / 3600),
                unit = WeatherUnit.HOUR
            ),
            minute = WeatherValueWithUnit(
                value = (this.weekly.sunshineDuration[selectedCalendarItemIndex] % 3600) / 60,
                unit = WeatherUnit.MINUTE
            )
        ),
        maxUvIndex = this.weekly.uvIndexMax[selectedCalendarItemIndex].roundToInt().toString(),
    )
}
