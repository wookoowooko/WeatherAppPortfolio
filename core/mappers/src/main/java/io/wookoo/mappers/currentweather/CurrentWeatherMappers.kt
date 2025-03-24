package io.wookoo.mappers.currentweather

import io.wookoo.database.dbo.CurrentWeatherEntity
import io.wookoo.database.dbo.DailyEntity
import io.wookoo.database.dbo.HourlyEntity
import io.wookoo.database.relations.WeatherWithDetails
import io.wookoo.domain.model.weather.current.CurrentDayModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.model.weather.current.DailyModel
import io.wookoo.domain.model.weather.current.HourlyModel
import io.wookoo.domain.model.weather.current.PrecipitationModel
import io.wookoo.domain.model.weather.current.SunCyclesModel
import io.wookoo.domain.model.weather.current.WindModel
import io.wookoo.network.dto.weather.current.CurrentWeatherDto
import io.wookoo.network.dto.weather.current.DailyDto
import io.wookoo.network.dto.weather.current.HourlyDto

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
    )
}

fun WeatherWithDetails.asCurrentWeatherResponseModel(): CurrentWeatherResponseModel {
    return CurrentWeatherResponseModel(
        cityName = geo.cityName,
        countryName = geo.countryName,
        geoItemId = geo.geoNameId,
        current = current.asCurrentDayModel(),
        hourly = hourly.asHourlyModel(),
        daily = daily.asDailyModel(),
        utcOffsetSeconds = geo.utcOffsetSeconds,
        time = current.time,
        isCurrentLocation = this.geo.isCurrent
    )
}

fun CurrentWeatherEntity.asCurrentDayModel(): CurrentDayModel {
    return CurrentDayModel(
        time = time,
        temperature = temperature,
        relativeHumidity = relativeHumidity,
        feelsLike = feelsLike,
        isDay = isDay,
        precipitation = PrecipitationModel(
            level = precipitation,
            rain = rain,
            showers = showers,
            snowfall = snowfall
        ),
        pressureMSL = pressureMSL,
        wind = WindModel(
            direction = windDirection,
            speed = windSpeed,
            gust = windGusts,
        ),
        weatherStatus = weatherCode,
        cloudCover = cloudCover,
    )
}

fun HourlyEntity.asHourlyModel(): HourlyModel {
    return HourlyModel(
        time = time,
        temperature = temperature,
        weatherCode = weatherCode,
        isDay = isDay.map { it == 1 }
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

fun DailyEntity.asDailyModel(): DailyModel {
    return DailyModel(
        sunCycles = SunCyclesModel(
            sunrise = sunrise,
            sunset = sunset
        ),
        uvIndexMax = uvIndexMax
    )
}

fun DailyDto.asDailyEntity(): DailyEntity {
    return DailyEntity(
        uvIndexMax = uvIndexMax,
        sunrise = sunrise,
        sunset = sunset,
        dailyId = 0,
        id = 0
    )
}
