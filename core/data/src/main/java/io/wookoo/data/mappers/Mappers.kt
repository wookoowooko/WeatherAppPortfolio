package io.wookoo.data.mappers

import io.wookoo.domain.model.CurrentDayModel
import io.wookoo.domain.model.CurrentWeatherResponseModel
import io.wookoo.domain.model.HourlyModel
import io.wookoo.domain.model.PrecipitationModel
import io.wookoo.domain.model.WindModel
import io.wookoo.network.dto.CurrentWeatherDto
import io.wookoo.network.dto.CurrentWeatherResponseDto
import io.wookoo.network.dto.HourlyDto

fun CurrentWeatherResponseDto.asCurrentWeatherResponseModel(): CurrentWeatherResponseModel {
    return CurrentWeatherResponseModel(
        latitude = latitude,
        longitude = longitude,
        timezone = timezone,
        current = current.asCurrentDayModel(),
        hourly = hourly.asHourlyModel(),
    )
}

private fun CurrentWeatherDto.asCurrentDayModel(): CurrentDayModel {
    return CurrentDayModel(
        time = time,
        temperature = temperature,
        relativeHumidity = relativeHumidity,
        feelsLike = feelsLike,
        isDay = isDay == 1,
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

private fun HourlyDto.asHourlyModel(): HourlyModel {
    return HourlyModel(
        time = time,
        temperature = temperature,
        weatherCode = weatherCode,
    )
}
