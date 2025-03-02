package io.wookoo.data.mappers

import io.wookoo.domain.model.geocoding.GeocodingResponseModel
import io.wookoo.domain.model.geocoding.GeocodingSearchModel
import io.wookoo.domain.model.reversegeocoding.ReverseGeocodingResponseModel
import io.wookoo.domain.model.reversegeocoding.ReverseGeocodingSearchModel
import io.wookoo.domain.model.weather.current.CurrentDayModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.model.weather.current.DailyModel
import io.wookoo.domain.model.weather.current.HourlyModel
import io.wookoo.domain.model.weather.current.PrecipitationModel
import io.wookoo.domain.model.weather.current.WindModel
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingSearchDto
import io.wookoo.network.dto.weather.current.CurrentWeatherDto
import io.wookoo.network.dto.weather.current.CurrentWeatherResponseDto
import io.wookoo.network.dto.weather.current.DailyDto
import io.wookoo.network.dto.weather.current.HourlyDto

fun CurrentWeatherResponseDto.asCurrentWeatherResponseModel(): CurrentWeatherResponseModel {
    return CurrentWeatherResponseModel(
        latitude = latitude,
        longitude = longitude,
        timezone = timezone,
        current = current.asCurrentDayModel(),
        hourly = hourly.asHourlyModel(),
        daily = daily.asDailyModel()
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
        isDay = isDay.map { it == 1 }
    )
}

private fun DailyDto.asDailyModel(): DailyModel {
    return DailyModel(
        sunrise = sunrise,
        sunset = sunset,
        uvIndexMax = uvIndexMax
    )
}

fun GeocodingResponseDto.asGeocodingResponseModel(): GeocodingResponseModel {
    return GeocodingResponseModel(
        results = results?.map { it.asGeocodingSearchModel() }.orEmpty()
    )
}

fun GeocodingSearchDto.asGeocodingSearchModel(): GeocodingSearchModel {
    return GeocodingSearchModel(
        cityName = name,
        latitude = latitude,
        longitude = longitude,
        countryCode = countryCode,
        country = country,
        urbanArea = admin1
    )
}

fun ReverseGeocodingResponseDto.asReverseGeocodingResponseModel(): ReverseGeocodingResponseModel {
    return ReverseGeocodingResponseModel(
        geonames = geonames?.map { it.asReverseGeocodingSearchModel() }.orEmpty()
    )
}

fun ReverseGeocodingSearchDto.asReverseGeocodingSearchModel(): ReverseGeocodingSearchModel {
    return ReverseGeocodingSearchModel(
        name = name,
        toponymName = toponymName,
        countryName = countryName,
        areaName = areaName.orEmpty(),
    )
}
