package io.wookoo.data.mappers.weeklyweather

import io.wookoo.domain.model.weather.current.SunCyclesModel
import io.wookoo.domain.model.weather.current.WindModel
import io.wookoo.domain.model.weekly.CurrentWeatherShortModel
import io.wookoo.domain.model.weekly.WeeklyWeatherModel
import io.wookoo.domain.model.weekly.WeeklyWeatherResponseModel
import io.wookoo.network.dto.weather.weekly.CurrentWeatherShortDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto

fun WeeklyWeatherResponseDto.asWeeklyWeatherResponseModel(): WeeklyWeatherResponseModel {
    return WeeklyWeatherResponseModel(
        currentShort = currentShort.asCurrentWeatherShortResponseModel(),
        weekly = week.asWeeklyWeatherModel()
    )
}

private fun CurrentWeatherShortDto.asCurrentWeatherShortResponseModel(): CurrentWeatherShortModel {
    return CurrentWeatherShortModel(
        isDay = isDay == 1
    )
}

private fun WeeklyWeatherDto.asWeeklyWeatherModel(): WeeklyWeatherModel {
    return WeeklyWeatherModel(
        time = time,
        weatherCode = weatherCode,
        tempMax = tempMax,
        tempMin = tempMin,
        apparentTempMax = apparentTempMax,
        apparentTempMin = apparentTempMin,
        dayLightDuration = dayLightDuration,
        snowfallSum = snowfallSum,
        sunshineDuration = sunshineDuration,
        uvIndexMax = uvIndexMax,
        rainSum = rainSum,
        showersSum = showersSum,
        precipitationSum = precipitationSum,
        precipitationProbabilityMax = precipitationProbabilityMax,
        sunCycles = SunCyclesModel(
            sunrise = sunrise,
            sunset = sunset
        ),
        windData = windSpeedMax.mapIndexed { index, speed ->
            WindModel(
                speed = speed,
                direction = windDirectionMax.getOrNull(index)?.toInt() ?: 0,
                gust = windGustsMax.getOrNull(index) ?: 0.0
            )
        }
    )
}
