package io.wookoo.main.uimappers

import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.units.ApiUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.usecases.ConvertDateUseCase
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.HourlyModelToHourlyListUseCase
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.main.uimodels.UiCurrentWeatherModel
import kotlin.math.roundToInt

fun CurrentWeatherResponseModel.asUICurrentWeather(
    hourlyModelToHourlyListUseCase: HourlyModelToHourlyListUseCase,
    convertDateUseCase: ConvertDateUseCase,
    convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
): UiCurrentWeatherModel {
    return UiCurrentWeatherModel(
        geoNameId = this.geoNameId,
        hourlyList = hourlyModelToHourlyListUseCase(hourlyModel = this.hourly),
        date = convertDateUseCase(this.current.time),
        isDay = this.current.isDay,
        humidity = WeatherValueWithUnit(
            value = this.current.relativeHumidity,
            unit = ApiUnit.PERCENT
        ),
        windSpeed = WeatherValueWithUnit(
            value = this.current.wind.speed,
            unit = ApiUnit.KMH
        ),
        windGust = WeatherValueWithUnit(
            value = this.current.wind.gust,
            unit = ApiUnit.KMH
        ),
        precipitation = WeatherValueWithUnit(
            value = this.current.precipitation.level,
            unit = ApiUnit.MM
        ),
        temperature = WeatherValueWithUnit(
            value = this.current.temperature,
            unit = ApiUnit.CELSIUS
        ),
        temperatureFeelsLike = WeatherValueWithUnit(
            value = this.current.feelsLike,
            unit = ApiUnit.CELSIUS
        ),
        pressureMsl = WeatherValueWithUnit(
            value = this.current.pressureMSL,
            unit = ApiUnit.PRESSURE
        ),
        uvIndex = this.daily.uvIndexMax.first().roundToInt().toString(),
        weatherStatus = convertWeatherCodeToEnumUseCase(this.current.weatherStatus),
        sunriseTime = convertUnixTimeUseCase.executeList(this.daily.sunCycles.sunrise).first(),
        sunsetTime = convertUnixTimeUseCase.executeList(this.daily.sunCycles.sunset).first(),
        windDirection = formatWindDirectionUseCase(this.current.wind.direction)
    )
}
