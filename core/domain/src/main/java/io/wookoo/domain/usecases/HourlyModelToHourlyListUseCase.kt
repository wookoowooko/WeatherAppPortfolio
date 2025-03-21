package io.wookoo.domain.usecases

import io.wookoo.domain.model.weather.current.HourlyModel
import io.wookoo.domain.model.weather.current.HourlyModelItem
import io.wookoo.domain.units.ApiUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class HourlyModelToHourlyListUseCase @Inject constructor(
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
) {

    operator fun invoke(
        hourlyModel: HourlyModel,
        utcOffsetSeconds: Long,
    ): List<HourlyModelItem> {
        val listOfTime: List<Long> = hourlyModel.time
        val convertedTimeList: List<String> = convertUnixTimeUseCase.executeList(listOfTime, utcOffsetSeconds)

        val listOfTemperature: List<Float> = hourlyModel.temperature
        val listOfIsDay: List<Boolean> = hourlyModel.isDay

        val listOfCode: List<Int> = hourlyModel.weatherCode

        return convertedTimeList.mapIndexed { index, time ->
            HourlyModelItem(
                time = time,
                temperature = WeatherValueWithUnit(
                    value = listOfTemperature[index],
                    unit = ApiUnit.CELSIUS
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
    private fun defineIsNow(input: Long, offset: Long): Boolean {
        val now = Clock.System.now()
            .plus(offset.toDuration(DurationUnit.SECONDS))
            .toLocalDateTime(TimeZone.UTC)
            .time
            .hour

        val inputTime = Instant.fromEpochSeconds((input + offset))
            .toLocalDateTime(TimeZone.UTC)
            .time
            .hour

        return now == inputTime
    }
}
