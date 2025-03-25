package io.wookoo.main.uimappers

import io.wookoo.domain.model.weather.current.CurrentWeatherDomain
import io.wookoo.domain.units.WeatherUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.usecases.ConvertDateUseCase
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.HourlyModelToHourlyListUseCase
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.main.uimodels.UiCurrentWeatherModel
import kotlin.math.roundToInt

fun CurrentWeatherDomain.asUICurrentWeather(
    hourlyModelToHourlyListUseCase: HourlyModelToHourlyListUseCase,
    convertDateUseCase: ConvertDateUseCase,
    convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
): UiCurrentWeatherModel {
    return UiCurrentWeatherModel(
        geoNameId = this.geo.geoItemId,
        hourlyList = hourlyModelToHourlyListUseCase(
            hourlyModel = this.hourly,
            utcOffsetSeconds = utcOffsetSeconds
        ),
        date = convertDateUseCase(this.current.time, utcOffsetSeconds),
        isDay = this.current.isDay,
        humidity = WeatherValueWithUnit(
            value = this.current.relativeHumidity,
            unit = WeatherUnit.PERCENT
        ),
        windSpeed = WeatherValueWithUnit(
            value = this.current.wind.speed,
            unit = WeatherUnit.KMH
        ),
        windGust = WeatherValueWithUnit(
            value = this.current.wind.gust,
            unit = WeatherUnit.KMH
        ),
        precipitation = WeatherValueWithUnit(
            value = this.current.precipitation.level,
            unit = WeatherUnit.MM
        ),
        temperature = WeatherValueWithUnit(
            value = this.current.temperature,
            unit = WeatherUnit.CELSIUS
        ),
        temperatureFeelsLike = WeatherValueWithUnit(
            value = this.current.feelsLike,
            unit = WeatherUnit.CELSIUS
        ),
        pressureMsl = WeatherValueWithUnit(
            value = this.current.pressureMSL,
            unit = WeatherUnit.PRESSURE
        ),
        uvIndex = this.daily.uvIndexMax.first().roundToInt().toString(),
        weatherStatus = convertWeatherCodeToEnumUseCase(this.current.weatherStatus),
        sunriseTime = convertUnixTimeUseCase.executeList(
            this.daily.sunCycles.sunrise,
            utcOffsetSeconds
        ).first(),
        sunsetTime = convertUnixTimeUseCase.executeList(
            this.daily.sunCycles.sunset,
            utcOffsetSeconds
        ).first(),
        windDirection = formatWindDirectionUseCase(this.current.wind.direction)
    )
}
