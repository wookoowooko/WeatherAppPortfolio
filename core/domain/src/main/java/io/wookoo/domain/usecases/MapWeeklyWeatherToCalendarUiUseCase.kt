package io.wookoo.domain.usecases

import io.wookoo.models.ui.UiCalendarDayModel
import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import javax.inject.Inject

class MapWeeklyWeatherToCalendarUiUseCase
@Inject constructor(
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val convertUnixDateToDayNameDayNumberUseCase: ConvertUnixDateToDayNameDayNumberUseCase
) {
    operator fun invoke(weekResponse: WeeklyWeatherDomainUI): List<UiCalendarDayModel> {
        return List(weekResponse.weekly.time.size) { index ->
            val timestamp = weekResponse.weekly.time[index]
            val (dayName, dayNumber) = convertUnixDateToDayNameDayNumberUseCase(timestamp)

            UiCalendarDayModel(
                dayName = dayName,
                dayNumber = dayNumber,
                weatherCondition = convertWeatherCodeToEnumUseCase(weekResponse.weekly.weatherCode[index]),
                isSelected = false,
                isToday = false,
                isDay = weekResponse.isDay
            )
        }
    }
}
