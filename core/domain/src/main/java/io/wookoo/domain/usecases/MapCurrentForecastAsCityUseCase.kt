package io.wookoo.domain.usecases

import io.wookoo.models.ui.UiCity
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.models.weather.current.CurrentWeatherDomain
import javax.inject.Inject

class MapCurrentForecastAsCityUseCase @Inject constructor(
    private val convertDateUseCase: ConvertDateUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val defineCorrectUnits: DefineCorrectUnitsUseCase,
) {
    suspend operator fun invoke(data: CurrentWeatherDomain): UiCity {
        val units = defineCorrectUnits.defineCorrectUnits()

        return UiCity(
            isCurrentLocation = data.isCurrentLocation,
            minTemperature = WeatherValueWithUnit(
                data.hourly.temperature.min(),
                units.temperature
            ),
            maxTemperature = WeatherValueWithUnit(
                data.hourly.temperature.max(),
                units.temperature
            ),
            cityName = data.geo.cityName,
            countryName = data.geo.countryName,
            temperature = WeatherValueWithUnit(
                value = data.current.temperature,
                units.temperature
            ),
            temperatureFeelsLike = WeatherValueWithUnit(
                value = data.current.feelsLike,
                units.temperature
            ),
            isDay = data.current.isDay,
            weatherStatus = convertWeatherCodeToEnumUseCase(data.current.weatherStatus),
            geoItemId = data.geo.geoItemId,
            date = convertDateUseCase(data.current.time, data.utcOffsetSeconds),
        )
    }
}
