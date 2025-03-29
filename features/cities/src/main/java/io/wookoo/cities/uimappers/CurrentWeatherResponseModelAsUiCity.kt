package io.wookoo.cities.uimappers

import io.wookoo.cities.uimodels.UiCity
import io.wookoo.models.weather.current.CurrentWeatherDomain
import io.wookoo.domain.usecases.ConvertDateUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherValueWithUnit

fun io.wookoo.models.weather.current.CurrentWeatherDomain.asUiCity(
    convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    convertDateUseCase: ConvertDateUseCase,
): UiCity {
    return UiCity(
        isCurrentLocation = this.isCurrentLocation,
        minTemperature = WeatherValueWithUnit(
            this.hourly.temperature.min(),
            WeatherUnit.CELSIUS
        ),
        maxTemperature = WeatherValueWithUnit(
            this.hourly.temperature.max(),
            WeatherUnit.CELSIUS
        ),
        cityName = this.geo.cityName,
        countryName = this.geo.countryName,
        temperature = WeatherValueWithUnit(
            value = this.current.temperature,
            unit = WeatherUnit.CELSIUS
        ),
        temperatureFeelsLike = WeatherValueWithUnit(
            value = this.current.feelsLike,
            unit = WeatherUnit.CELSIUS
        ),
        isDay = this.current.isDay,
        weatherStatus = convertWeatherCodeToEnumUseCase(this.current.weatherStatus),
        geoItemId = this.geo.geoItemId,
        date = convertDateUseCase(this.current.time, utcOffsetSeconds),
    )
}
