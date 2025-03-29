package io.wookoo.settings.mvi

import io.wookoo.domain.model.weather.WeatherDataUnit
import io.wookoo.domain.units.WeatherUnit

data class SettingsState(

    val temperatureUnitOptions: List<WeatherDataUnit> = listOf(
        WeatherUnit.CELSIUS,
        WeatherUnit.FAHRENHEIT
    ).map { it.toWeatherDataUnit() },
    val windSpeedUnitOptions: List<WeatherDataUnit> = listOf(
        WeatherUnit.KMH,
        WeatherUnit.MPH
    ).map { it.toWeatherDataUnit() },
    val precipitationUnitOptions: List<WeatherDataUnit> = listOf(
        WeatherUnit.MM,
        WeatherUnit.INCH
    ).map { it.toWeatherDataUnit() },

    val selectedTemperatureUnit: WeatherDataUnit = WeatherUnit.CELSIUS.toWeatherDataUnit(),
    val selectedWindSpeedUnit: WeatherDataUnit = WeatherUnit.KMH.toWeatherDataUnit(),
    val selectedPrecipitationUnit: WeatherDataUnit = WeatherUnit.MM.toWeatherDataUnit(),
)

fun WeatherUnit.toWeatherDataUnit() = WeatherDataUnit(this.apiValue, this)
