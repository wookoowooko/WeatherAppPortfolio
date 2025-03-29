package io.wookoo.domain.usecases

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.model.weather.current.CurrentWeatherDomain
import io.wookoo.domain.model.weather.current.additional.HourlyModelItem
import io.wookoo.domain.units.WeatherUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.units.WindDirection
import javax.inject.Inject
import kotlin.math.roundToInt

class PrepareCurrentForecast @Inject constructor(
    private val hourlyModelToHourlyListUseCase: HourlyModelToHourlyListUseCase,
    private val convertDateUseCase: ConvertDateUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    private val formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
    private val defineCorrectUnits: DefineCorrectUnitsUseCase,
) {
    suspend operator fun invoke(data: CurrentWeatherDomain): UiCurrentWeatherModel {

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

data class UiCurrentWeatherModel(
    val city: String = "",
    val country: String = "",
    val geoNameId: Long = 0L,
    val date: String = "",
    val isDay: Boolean = true,
    val humidity: WeatherValueWithUnit = WeatherValueWithUnit(),
    val windSpeed: WeatherValueWithUnit = WeatherValueWithUnit(),
    val windDirection: WindDirection = WindDirection.UNDETECTED,
    val windGust: WeatherValueWithUnit = WeatherValueWithUnit(),
    val precipitation: WeatherValueWithUnit = WeatherValueWithUnit(),
    val pressureMsl: WeatherValueWithUnit = WeatherValueWithUnit(),
    val temperature: WeatherValueWithUnit = WeatherValueWithUnit(),
    val temperatureFeelsLike: WeatherValueWithUnit = WeatherValueWithUnit(),
    val weatherStatus: WeatherCondition = WeatherCondition.UNKNOWN,
    val hourlyList: List<HourlyModelItem> = emptyList(),
    val sunriseTime: String = "",
    val sunsetTime: String = "",
    val uvIndex: String = "",
)
