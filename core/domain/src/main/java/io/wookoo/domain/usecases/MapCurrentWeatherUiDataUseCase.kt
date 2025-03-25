package io.wookoo.domain.usecases

import io.wookoo.domain.model.weather.current.CurrentWeatherDomain
import io.wookoo.domain.model.weather.current.CurrentWeatherUi
import io.wookoo.domain.units.WeatherUnit
import io.wookoo.domain.utils.IStringProvider
import javax.inject.Inject
import kotlin.math.roundToInt

class MapCurrentWeatherUiDataUseCase @Inject constructor(
    private val convertDateUseCase: ConvertDateUseCase,
    private val formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val hourlyModelToHourlyListUseCase: HourlyModelToHourlyListUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    private val stringProvider: IStringProvider,
    private val unitFormatUseCase: UnitFormatUseCase
) {
    operator fun invoke(
        clearDomain: CurrentWeatherDomain,
    ): CurrentWeatherUi {
        return CurrentWeatherUi(
            geoNameId = clearDomain.geo.geoItemId,
            hourlyList = hourlyModelToHourlyListUseCase(
                hourlyModel = clearDomain.hourly,
                utcOffsetSeconds = clearDomain.utcOffsetSeconds
            ),
            isDay = clearDomain.current.isDay,
            date = convertDateUseCase(clearDomain.time, clearDomain.utcOffsetSeconds),
            humidity = unitFormatUseCase(
                value = clearDomain.current.relativeHumidity,
                unit = WeatherUnit.PERCENT
            ),
            windSpeed = unitFormatUseCase(
                value = clearDomain.current.wind.speed,
                unit = WeatherUnit.KMH
            ),
            windGust = unitFormatUseCase(
                value = clearDomain.current.wind.gust,
                unit = WeatherUnit.KMH
            ),
            precipitation = unitFormatUseCase(
                value = clearDomain.current.precipitation.level,
                unit = WeatherUnit.MM
            ),
            temperature = unitFormatUseCase(
                clearDomain.current.temperature,
                WeatherUnit.CELSIUS
            ),
            temperatureFeelsLike = unitFormatUseCase(
                value = clearDomain.current.feelsLike,
                unit = WeatherUnit.CELSIUS
            ),
            pressureMsl = unitFormatUseCase(
                value = clearDomain.current.pressureMSL,
                unit = WeatherUnit.PRESSURE
            ),
            uvIndex = clearDomain.daily.uvIndexMax.first().roundToInt().toString(),
            weatherStatus = Pair(
                stringProvider.fromWeatherCondition(
                    convertWeatherCodeToEnumUseCase(clearDomain.current.weatherStatus),
                    clearDomain.current.isDay
                ),
                convertWeatherCodeToEnumUseCase(clearDomain.current.weatherStatus)
            ),

            sunriseTime = convertUnixTimeUseCase.executeList(
                clearDomain.daily.sunCycles.sunrise,
                clearDomain.utcOffsetSeconds
            ).first(),
            sunsetTime = convertUnixTimeUseCase.executeList(
                clearDomain.daily.sunCycles.sunset,
                clearDomain.utcOffsetSeconds
            ).first(),
            windDirection = stringProvider.fromWindDirection(
                formatWindDirectionUseCase(clearDomain.current.wind.direction)
            )
        )
    }
}
