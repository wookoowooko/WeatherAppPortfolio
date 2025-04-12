package io.wookoo.mappers.currentweather

import io.wookoo.database.dbo.CurrentWeatherEntity
import io.wookoo.database.dbo.DailyEntity
import io.wookoo.database.dbo.HourlyEntity
import io.wookoo.network.dto.weather.current.CurrentWeatherDto
import io.wookoo.network.dto.weather.current.DailyDto
import io.wookoo.network.dto.weather.current.HourlyDto

object FromApiToDatabase {

    fun CurrentWeatherDto.asCurrentWeatherEntity(): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            time = time,
            temperature = temperature,
            relativeHumidity = relativeHumidity,
            feelsLike = feelsLike,
            isDay = isDay == 1,
            pressureMSL = pressureMSL,
            cloudCover = cloudCover,
            precipitation = precipitation,
            rain = rain,
            showers = showers,
            snowfall = snowfall,
            windDirection = windDirection,
            windSpeed = windSpeed,
            windGusts = windGusts,
            weatherCode = weatherCode,
            currentId = 0,
            id = 0,
            uvIndex = uvIndex
        )
    }

    fun DailyDto.asDailyEntity(): DailyEntity {
        return DailyEntity(
            sunrise = sunrise,
            sunset = sunset,
            dailyId = 0,
            id = 0
        )
    }

    fun HourlyDto.asHourlyEntity(): HourlyEntity {
        return HourlyEntity(
            time = time,
            temperature = temperature,
            weatherCode = weatherCode,
            isDay = isDay,
            hourlyId = 0,
            id = 0
        )
    }
}
