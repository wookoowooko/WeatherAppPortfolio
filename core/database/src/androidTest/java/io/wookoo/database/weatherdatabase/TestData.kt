package io.wookoo.database.weatherdatabase

import io.wookoo.database.dbo.CurrentWeatherEntity
import io.wookoo.database.dbo.DailyEntity
import io.wookoo.database.dbo.GeoEntity
import io.wookoo.database.dbo.HourlyEntity
import io.wookoo.database.dbo.weekly.WeeklyWeatherEntity
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
        weatherCode = 200,
        uvIndex = 3.0,
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
    )

    val testWeatherWithDetails = WeatherWithDetails(
        geoEntity,
        currentWeather,
        hourlyWeather,
        dailyWeather
    )

    val testWeeklyForecastEntity = WeeklyWeatherEntity(
        geoItemId = 1L,
        cityName = "Moscow",
        isDay = true,
        time = listOf(1609459200L, 1609462800L, 1609466400L),
        weatherCode = listOf(200, 201, 202),
        tempMax = listOf(14.0, 15.0, 16.0),
        tempMin = listOf(10.0, 11.0, 12.0),
        apparentTempMax = listOf(12.0, 13.0, 14.0),
        apparentTempMin = listOf(8.0, 9.0, 10.0),
        sunset = listOf(1609490400L),
        sunrise = listOf(1609444800L),
        dayLightDuration = listOf(200.0, 201.0, 202.0),
        sunshineDuration = listOf(200.0, 201.0, 202.0),
        uvIndexMax = listOf(6.0, 7.0, 8.0),
        precipitationSum = listOf(1.0, 2.0, 3.0),
        rainSum = listOf(1.0, 2.0, 3.0),
        snowfallSum = listOf(1.0, 2.0, 3.0),
        showersSum = listOf(1.0, 2.0, 3.0),
        precipitationProbabilityMax = listOf(1, 2, 3),
        windSpeedMax = listOf(1.0, 2.0, 3.0),
        windDirectionMax = listOf(1, 2, 3),
        windGustsMax = listOf(1.0, 2.0, 3.0),
        utcOffsetSeconds = 123124124L
    )
}
