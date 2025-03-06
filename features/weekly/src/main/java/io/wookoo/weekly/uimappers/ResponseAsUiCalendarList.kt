package io.wookoo.weekly.uimappers

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.usecases.ConvertUnixDateToDayNameDayNumberUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.weekly.uimodels.UiCalendarDayModel

fun WeeklyWeatherResponseModel.asUiCalendarList(

    convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    convertUnixDateToDayNameDayNumberUseCase: ConvertUnixDateToDayNameDayNumberUseCase,
) = List(this.weekly.time.size) { index ->

    val timestamp = this.weekly.time[index]
    val (dayName, dayNumber) = convertUnixDateToDayNameDayNumberUseCase(timestamp)

    UiCalendarDayModel(
        dayName = dayName,
        dayNumber = dayNumber,
        weatherCondition = convertWeatherCodeToEnumUseCase(this.weekly.weatherCode[index]),
        isSelected = false,
        isToday = false,
        isDay = this.currentShort.isDay
    )
}
