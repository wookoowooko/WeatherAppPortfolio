package io.wookoo.database.weatherdatabase

import io.wookoo.database.dbo.CurrentWeatherEntity
import io.wookoo.database.dbo.DailyEntity
import io.wookoo.database.dbo.GeoEntity
import io.wookoo.database.dbo.HourlyEntity
import io.wookoo.database.relations.WeatherWithDetails

internal object TestData {

    val geoEntity = GeoEntity(
        geoItemId = 1L,
        cityName = "Moscow",
        countryName = "Russia",
        utcOffsetSeconds = 10800,
        isCurrent = true
    )

    val currentWeather = CurrentWeatherEntity(
        currentId = 1L,
        temperature = 15.5,
        windSpeed = 5.2,
        lastUpdate = System.currentTimeMillis(),
        id = 1L,
        time = 0L,
        relativeHumidity = 80,
        feelsLike = 10.0,
        isDay = true,
        precipitation = 1.5,
        rain = 1.0,
        showers = 0.5,
        snowfall = 0.0,
        cloudCover = 50,
        pressureMSL = 1013.25,
        windDirection = 270,
        windGusts = 5.0,
        weatherCode = 200
    )

    val hourlyWeather = HourlyEntity(
        id = 1L,
        hourlyId = 1L,
        time = listOf(1609459200L, 1609462800L, 1609466400L),
        temperature = listOf(14.0f, 15.0f, 16.0f),
        weatherCode = listOf(200, 201, 202),
        isDay = listOf(1, 1, 1)
    )

    val dailyWeather = DailyEntity(
        id = 1L,
        dailyId = 1L,
        sunrise = listOf(1609444800L),
        sunset = listOf(1609490400L),
        uvIndexMax = listOf(6.0f)
    )

    val testWeatherWithDetails = WeatherWithDetails(
        geoEntity,
        currentWeather,
        hourlyWeather,
        dailyWeather
    )
}
