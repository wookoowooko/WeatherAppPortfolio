package io.wookoo.mappers.currentweather

import io.wookoo.database.dbo.CurrentWeatherEntity
import io.wookoo.database.dbo.DailyEntity
import io.wookoo.database.dbo.HourlyEntity
import io.wookoo.database.relations.WeatherWithDetails
import io.wookoo.domain.model.geocoding.GeocodingDomainUI
import io.wookoo.domain.model.weather.current.CurrentWeatherDomain
import io.wookoo.domain.model.weather.current.additional.CurrentDayModel
import io.wookoo.domain.model.weather.current.additional.DailyModel
import io.wookoo.domain.model.weather.current.additional.HourlyModel
import io.wookoo.domain.model.weather.current.additional.PrecipitationModel
import io.wookoo.domain.model.weather.current.additional.SunCyclesModel
import io.wookoo.domain.model.weather.current.additional.WindModel

object FromDatabaseToUi {

    fun WeatherWithDetails.asCurrentWeatherDomainUi(): CurrentWeatherDomain {
        return CurrentWeatherDomain(
            geo = GeocodingDomainUI(
                geoItemId = geo.geoNameId,
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

    private fun CurrentWeatherEntity.asCurrentDayModel(): CurrentDayModel {
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

    private fun HourlyEntity.asHourlyModel(): HourlyModel {
        return HourlyModel(
            time = time,
            temperature = temperature,
            weatherCode = weatherCode,
            isDay = isDay.map { it == 1 }
        )
    }

    private fun DailyEntity.asDailyModel(): DailyModel {
        return DailyModel(
            sunCycles = SunCyclesModel(
                sunrise = sunrise,
                sunset = sunset
            ),
            uvIndexMax = uvIndexMax
        )
    }
}
