package io.wookoo.mappers.currentweather

import io.wookoo.database.dbo.CurrentWeatherEntity
import io.wookoo.database.dbo.DailyEntity
import io.wookoo.database.dbo.HourlyEntity
import io.wookoo.database.relations.WeatherWithDetails

object FromDatabaseToUi {

    fun WeatherWithDetails.asCurrentWeatherDomainUi(): io.wookoo.models.weather.current.CurrentWeatherDomain {
        return io.wookoo.models.weather.current.CurrentWeatherDomain(
            geo = io.wookoo.models.geocoding.GeocodingDomainUI(
                geoItemId = geo.geoItemId,
                cityName = geo.cityName,
                latitude = null,
                longitude = null,
                countryName = geo.countryName
            ),
            current = current.asCurrentDayModel(),
            hourly = hourly.asHourlyModel(),
            daily = daily.asDailyModel(),
            utcOffsetSeconds = geo.utcOffsetSeconds,
            time = current.time,
            isCurrentLocation = this.geo.isCurrent
        )
    }

    private fun CurrentWeatherEntity.asCurrentDayModel(): io.wookoo.models.weather.current.additional.CurrentDayModel {
        return io.wookoo.models.weather.current.additional.CurrentDayModel(
            time = time,
            temperature = temperature,
            relativeHumidity = relativeHumidity,
            feelsLike = feelsLike,
            isDay = isDay,
            precipitation = io.wookoo.models.weather.current.additional.PrecipitationModel(
                level = precipitation,
                rain = rain,
                showers = showers,
                snowfall = snowfall
            ),
            pressureMSL = pressureMSL,
            wind = io.wookoo.models.weather.current.additional.WindModel(
                direction = windDirection,
                speed = windSpeed,
                gust = windGusts,
            ),
            weatherStatus = weatherCode,
            cloudCover = cloudCover,
        )
    }

    private fun HourlyEntity.asHourlyModel(): io.wookoo.models.weather.current.additional.HourlyModel {
        return io.wookoo.models.weather.current.additional.HourlyModel(
            time = time,
            temperature = temperature,
            weatherCode = weatherCode,
            isDay = isDay.map { it == 1 }
        )
    }

    private fun DailyEntity.asDailyModel(): io.wookoo.models.weather.current.additional.DailyModel {
        return io.wookoo.models.weather.current.additional.DailyModel(
            sunCycles = io.wookoo.models.weather.current.additional.SunCyclesModel(
                sunrise = sunrise,
                sunset = sunset
            ),
            uvIndexMax = uvIndexMax
        )
    }
}
