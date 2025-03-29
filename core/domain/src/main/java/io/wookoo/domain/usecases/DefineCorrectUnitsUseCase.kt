package io.wookoo.domain.usecases

import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.models.units.WeatherUnit
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefineCorrectUnitsUseCase @Inject constructor(
    private val dataStore: IDataStoreRepo,
) {
    suspend fun defineCorrectUnits(): WeatherUnits {

        val settings = dataStore.userSettings.first()
        return WeatherUnits(
            temperature = when (settings.temperatureUnit) {
                WeatherUnit.CELSIUS.apiValue -> WeatherUnit.CELSIUS
                WeatherUnit.FAHRENHEIT.apiValue -> WeatherUnit.FAHRENHEIT
                else -> WeatherUnit.CELSIUS
            },
            precipitation = when (settings.precipitationUnit) {
                WeatherUnit.MM.apiValue -> WeatherUnit.MM
                WeatherUnit.INCH.apiValue -> WeatherUnit.INCH
                else -> WeatherUnit.MM
            },
            windSpeed = when (settings.windSpeedUnit) {
                WeatherUnit.KMH.apiValue -> WeatherUnit.KMH
                WeatherUnit.MPH.apiValue -> WeatherUnit.MPH
                else -> WeatherUnit.KMH
            }
        )
    }

    data class WeatherUnits(
        val temperature: WeatherUnit,
        val precipitation: WeatherUnit,
        val windSpeed: WeatherUnit,
    )
}