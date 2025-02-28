package io.wookoo.domain.usecases

import io.wookoo.domain.enums.ApiUnit
import io.wookoo.domain.model.weather.current.HourlyModel
import io.wookoo.domain.model.weather.current.HourlyModelItem
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class HourlyModelToHourlyListUseCase @Inject constructor(
    private val unitFormatUseCase: UnitFormatUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
) {

    operator fun invoke(
        hourlyModel: HourlyModel,
    ): List<HourlyModelItem> {
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val currentHour = currentTime.hour

        val listOfTime: List<Long> = hourlyModel.time
        val convertedTimeList: List<String> = convertUnixTimeUseCase(listOfTime)

        val listOfTemperature: List<Float> = hourlyModel.temperature
        val listOfIsDay: List<Boolean> = hourlyModel.isDay

        val listOfCode: List<Int> = hourlyModel.weatherCode

        return convertedTimeList.mapIndexed { index, time ->
            HourlyModelItem(
                time = time,
                temperature = unitFormatUseCase(listOfTemperature[index], ApiUnit.CELSIUS),
                weatherCode = convertWeatherCodeToEnumUseCase(listOfCode[index]),
                isNow = time == convertedTimeList[currentHour],
                isDay = listOfIsDay.getOrElse(index) { false },
            )
        }
    }
}
