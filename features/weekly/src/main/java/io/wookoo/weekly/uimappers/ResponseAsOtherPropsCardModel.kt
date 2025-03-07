package io.wookoo.weekly.uimappers

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.units.ApiUnit
import io.wookoo.domain.units.SecondsDuration
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.weekly.uimodels.UiOtherPropsModel
import kotlin.math.roundToInt

fun WeeklyWeatherResponseModel.asOtherPropsCardModel(
    selectedCalendarItemIndex: Int,
): UiOtherPropsModel {
    return UiOtherPropsModel(
        dayLightDuration = SecondsDuration(
            hour = WeatherValueWithUnit(
                value = (this.weekly.dayLightDuration[selectedCalendarItemIndex] / 3600),
                unit = ApiUnit.HOUR
            ),
            minute = WeatherValueWithUnit(
                value = (this.weekly.dayLightDuration[selectedCalendarItemIndex] % 3600) / 60,
                unit = ApiUnit.MINUTE
            )
        ),
        sunShineDuration = SecondsDuration(
            hour = WeatherValueWithUnit(
                value = (this.weekly.sunshineDuration[selectedCalendarItemIndex] / 3600),
                unit = ApiUnit.HOUR
            ),
            minute = WeatherValueWithUnit(
                value = (this.weekly.sunshineDuration[selectedCalendarItemIndex] % 3600) / 60,
                unit = ApiUnit.MINUTE
            )
        ),
        maxUvIndex = this.weekly.uvIndexMax[selectedCalendarItemIndex].roundToInt().toString(),
    )
}
