package io.wookoo.domain.usecases

import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.domain.utils.defineIsNow
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.models.weather.current.additional.HourlyModel
import io.wookoo.models.weather.current.additional.HourlyModelItem
import javax.inject.Inject

@CoveredByTest
class ConvertHourlyModelToHourlyListUseCase @Inject constructor(
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val defineCorrectUnitsUseCase: DefineCorrectUnitsUseCase,
) {

    suspend operator fun invoke(
        hourlyModel: HourlyModel,
        utcOffsetSeconds: Long,
    ): List<HourlyModelItem> {
        val units = defineCorrectUnitsUseCase.defineCorrectUnits()

        val listOfTime: List<Long> = hourlyModel.time
        val convertedTimeList: List<String> =
            convertUnixTimeUseCase.executeList(listOfTime, utcOffsetSeconds)

        val listOfTemperature: List<Float> = hourlyModel.temperature
        val listOfIsDay: List<Boolean> = hourlyModel.isDay

        val listOfCode: List<Int> = hourlyModel.weatherCode

        return convertedTimeList.mapIndexed { index, time ->
            HourlyModelItem(
                time = time,
                temperature = WeatherValueWithUnit(
                    value = listOfTemperature[index],
                    unit = units.temperature
                ),
                weatherCode = convertWeatherCodeToEnumUseCase(listOfCode[index]),
                isNow = defineIsNow(
                    input = listOfTime[index],
                    offset = utcOffsetSeconds
                ),
                isDay = listOfIsDay.getOrElse(index) { false },
            )
        }
    }
}
