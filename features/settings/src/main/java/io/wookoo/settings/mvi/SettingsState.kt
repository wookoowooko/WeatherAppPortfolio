package io.wookoo.settings.mvi

import io.wookoo.models.units.WeatherUnit

data class SettingsState(

    val temperatureUnitOptions: List<io.wookoo.models.units.WeatherDataUnit> = listOf(
        WeatherUnit.CELSIUS,
        WeatherUnit.FAHRENHEIT
    ).map { it.toWeatherDataUnit() },
    val windSpeedUnitOptions: List<io.wookoo.models.units.WeatherDataUnit> = listOf(
        WeatherUnit.KMH,
        WeatherUnit.MPH,
        WeatherUnit.MS
    ).map { it.toWeatherDataUnit() },
    val precipitationUnitOptions: List<io.wookoo.models.units.WeatherDataUnit> = listOf(
        WeatherUnit.MM,
        WeatherUnit.INCH
    ).map { it.toWeatherDataUnit() },

    val selectedTemperatureUnit: io.wookoo.models.units.WeatherDataUnit = WeatherUnit.CELSIUS.toWeatherDataUnit(),
    val selectedWindSpeedUnit: io.wookoo.models.units.WeatherDataUnit = WeatherUnit.KMH.toWeatherDataUnit(),
    val selectedPrecipitationUnit: io.wookoo.models.units.WeatherDataUnit = WeatherUnit.MM.toWeatherDataUnit(),
)

fun WeatherUnit.toWeatherDataUnit() = io.wookoo.models.units.WeatherDataUnit(this.apiValue, this)
