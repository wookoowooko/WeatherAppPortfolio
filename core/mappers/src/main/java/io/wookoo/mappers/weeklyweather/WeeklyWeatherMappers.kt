package io.wookoo.mappers.weeklyweather

import io.wookoo.database.dbo.weekly.WeeklyWeatherEntity
import io.wookoo.domain.model.weather.current.PrecipitationModel
import io.wookoo.domain.model.weather.current.SunCyclesModel
import io.wookoo.domain.model.weather.current.WindModel
import io.wookoo.domain.model.weather.weekly.CurrentWeatherShortModel
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherModel
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.network.dto.weather.weekly.CurrentWeatherShortDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto

fun WeeklyWeatherResponseDto.asWeeklyWeatherResponseModel(): WeeklyWeatherResponseModel {
    return WeeklyWeatherResponseModel(
        latitude = latitude,
        longitude = longitude,
        currentShort = currentShort.asCurrentWeatherShortResponseModel(),
        weekly = week.asWeeklyWeatherModel()
    )
}

fun WeeklyWeatherEntity.asWeeklyWeatherResponseModel(): WeeklyWeatherResponseModel {
    return WeeklyWeatherResponseModel(
        currentShort = CurrentWeatherShortModel(
            isDay = isDay
        ),
        weekly = this.asWeeklyWeatherModel(),
        latitude = latitude,
        longitude = longitude

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
        sunshineDuration = sunshineDuration,
        uvIndexMax = uvIndexMax,
        precipitationProbabilityMax = precipitationProbabilityMax,
        precipitationData = precipitationSum.mapIndexed { index, sum ->
            PrecipitationModel(
                level = sum,
                rain = rainSum.getOrNull(index) ?: 0.0,
                showers = showersSum.getOrNull(index) ?: 0.0,
                snowfall = snowfallSum.getOrNull(index) ?: 0.0
            )
        },
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

fun WeeklyWeatherEntity.asWeeklyWeatherModel(): WeeklyWeatherModel {
    return WeeklyWeatherModel(
        time = time,
        weatherCode = weatherCode,
        tempMax = tempMax,
        tempMin = tempMin,
        apparentTempMax = apparentTempMax,
        apparentTempMin = apparentTempMin,
        dayLightDuration = dayLightDuration,
        sunshineDuration = sunshineDuration,
        uvIndexMax = uvIndexMax,
        precipitationProbabilityMax = precipitationProbabilityMax,
        precipitationData = precipitationSum.mapIndexed { index, sum ->
            PrecipitationModel(
                level = sum,
                rain = rainSum.getOrNull(index) ?: 0.0,
                showers = showersSum.getOrNull(index) ?: 0.0,
                snowfall = snowfallSum.getOrNull(index) ?: 0.0
            )
        },
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

fun WeeklyWeatherDto.asWeeklyWeatherEntity(
    isDay: Boolean,
    geoNameId: Long,
    latitude: Double,
    longitude: Double,
    cityName: String
): WeeklyWeatherEntity {
    return WeeklyWeatherEntity(
        time = time,
        weatherCode = weatherCode,
        tempMax = tempMax,
        tempMin = tempMin,
        apparentTempMax = apparentTempMax,
        apparentTempMin = apparentTempMin,
        dayLightDuration = dayLightDuration,
        sunshineDuration = sunshineDuration,
        uvIndexMax = uvIndexMax,
        precipitationProbabilityMax = precipitationProbabilityMax,
        precipitationSum = precipitationSum,
        rainSum = rainSum,
        showersSum = showersSum,
        snowfallSum = snowfallSum,
        sunrise = sunrise,
        sunset = sunset,
        windSpeedMax = windSpeedMax,
        windGustsMax = windGustsMax,
        windDirectionMax = windDirectionMax,
        isDay = isDay,
        geoNameId = geoNameId,
        latitude = latitude,
        longitude = longitude,
        cityName = cityName
    )
}
