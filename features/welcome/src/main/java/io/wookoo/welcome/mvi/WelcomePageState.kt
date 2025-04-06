package io.wookoo.welcome.mvi

import io.wookoo.domain.utils.AppError
import io.wookoo.models.geocoding.GeocodingDomainUI
import io.wookoo.models.units.WeatherUnit

data class WelcomePageState(
    val isOffline: Boolean = false,
    val geoItem: GeocodingDomainUI? = null,
    val isSearchExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: AppError? = null,
    val searchQuery: String = "",
    val results: List<GeocodingDomainUI> = emptyList(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isGeolocationSearchInProgress: Boolean = false,
    val isDialogVisible: Boolean = false,

    val temperatureUnitOptions: List<WeatherDataUnit> = listOf(
        WeatherUnit.CELSIUS,
        WeatherUnit.FAHRENHEIT
    ).map { it.toWeatherDataUnit() },
    val windSpeedUnitOptions: List<WeatherDataUnit> = listOf(
        WeatherUnit.KMH,
        WeatherUnit.MPH,
        WeatherUnit.MS
    ).map { it.toWeatherDataUnit() },
    val precipitationUnitOptions: List<WeatherDataUnit> = listOf(
        WeatherUnit.MM,
        WeatherUnit.INCH
    ).map { it.toWeatherDataUnit() },

    val selectedTemperatureUnit: WeatherDataUnit = WeatherUnit.CELSIUS.toWeatherDataUnit(),
    val selectedWindSpeedUnit: WeatherDataUnit = WeatherUnit.KMH.toWeatherDataUnit(),
    val selectedPrecipitationUnit: WeatherDataUnit = WeatherUnit.MM.toWeatherDataUnit(),
)

data class WeatherDataUnit(
    val apiValue: String,
    val stringValue: WeatherUnit,
)

fun WeatherUnit.toWeatherDataUnit() = WeatherDataUnit(this.apiValue, this)
