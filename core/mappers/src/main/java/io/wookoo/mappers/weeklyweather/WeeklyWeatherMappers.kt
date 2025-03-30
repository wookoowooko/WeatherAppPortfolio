package io.wookoo.mappers.weeklyweather

import io.wookoo.database.dbo.weekly.WeeklyWeatherEntity
import io.wookoo.models.weather.current.additional.PrecipitationModel
import io.wookoo.models.weather.current.additional.SunCyclesModel
import io.wookoo.models.weather.current.additional.WindModel
import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.models.weather.weekly.additional.WeeklyWeatherModel
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherDto

fun WeeklyWeatherEntity.asWeeklyWeatherResponseModel(): WeeklyWeatherDomainUI {
    return WeeklyWeatherDomainUI(
        isDay = isDay,
        weekly = this.asWeeklyWeatherModel(),
        utcOffsetSeconds = this.utcOffsetSeconds

    )
}

fun WeeklyWeatherEntity.asWeeklyWeatherModel(): WeeklyWeatherModel {
    return WeeklyWeatherModel(
        cityName = cityName,
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
    cityName: String,
    utcOffsetSeconds: Long,
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
        geoItemId = geoNameId,
        cityName = cityName,
        utcOffsetSeconds = utcOffsetSeconds
    )
}
