package io.wookoo.domain.usecases

import io.wookoo.models.ui.UiCurrentWeatherModel
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherValueWithUnit
import javax.inject.Inject
import kotlin.math.roundToInt

class PrepareCurrentForecastUseCase @Inject constructor(
    private val hourlyModelToHourlyListUseCase: HourlyModelToHourlyListUseCase,
    private val convertDateUseCase: ConvertDateUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    private val formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
    private val defineCorrectUnits: DefineCorrectUnitsUseCase,
) {
    suspend operator fun invoke(data: io.wookoo.models.weather.current.CurrentWeatherDomain): UiCurrentWeatherModel {

        val units = defineCorrectUnits.defineCorrectUnits()

        return UiCurrentWeatherModel(
            city = data.geo.cityName,
            country = data.geo.countryName,
            geoNameId = data.geo.geoItemId,
            hourlyList = hourlyModelToHourlyListUseCase(
                hourlyModel = data.hourly,
                utcOffsetSeconds = data.utcOffsetSeconds
            ),
            date = convertDateUseCase(data.current.time, data.utcOffsetSeconds),
            isDay = data.current.isDay,
            humidity = WeatherValueWithUnit(
                value = data.current.relativeHumidity,
                unit = WeatherUnit.PERCENT
            ),
            windSpeed = WeatherValueWithUnit(
                value = data.current.wind.speed,
                unit = units.windSpeed
            ),
            windGust = WeatherValueWithUnit(
                value = data.current.wind.gust,
                unit = units.windSpeed
            ),
            precipitation = WeatherValueWithUnit(
                value = data.current.precipitation.level,
                unit = units.precipitation
            ),
            temperature = WeatherValueWithUnit(
                value = data.current.temperature,
                unit = units.temperature
            ),
            temperatureFeelsLike = WeatherValueWithUnit(
                value = data.current.feelsLike,
                unit = units.temperature
            ),
            pressureMsl = WeatherValueWithUnit(
                value = data.current.pressureMSL,
                unit = WeatherUnit.PRESSURE
            ),
            uvIndex = data.daily.uvIndexMax.first().roundToInt().toString(),
            weatherStatus = convertWeatherCodeToEnumUseCase(data.current.weatherStatus),
            sunriseTime = convertUnixTimeUseCase.executeList(
                data.daily.sunCycles.sunrise,
                data.utcOffsetSeconds
            ).first(),
            sunsetTime = convertUnixTimeUseCase.executeList(
                data.daily.sunCycles.sunset,
                data.utcOffsetSeconds
            ).first(),
            windDirection = formatWindDirectionUseCase(data.current.wind.direction)
        )
    }
}

