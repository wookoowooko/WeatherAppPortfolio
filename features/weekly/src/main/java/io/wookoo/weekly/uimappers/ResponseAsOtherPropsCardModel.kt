package io.wookoo.weekly.uimappers

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.domain.units.SecondsDuration
import io.wookoo.domain.units.WeatherUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.weekly.uimodels.UiOtherPropsModel
import kotlin.math.roundToInt

fun WeeklyWeatherDomainUI.asOtherPropsCardModel(
    selectedCalendarItemIndex: Int,
): UiOtherPropsModel {
    return UiOtherPropsModel(
        dayLightDuration = SecondsDuration(
            hour = WeatherValueWithUnit(
                value = (this.weekly.dayLightDuration[selectedCalendarItemIndex] / 3600),
                unit = WeatherUnit.HOUR
            ),
            minute = WeatherValueWithUnit(
                value = (this.weekly.dayLightDuration[selectedCalendarItemIndex] % 3600) / 60,
                unit = WeatherUnit.MINUTE
            )
        ),
        sunShineDuration = SecondsDuration(
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
