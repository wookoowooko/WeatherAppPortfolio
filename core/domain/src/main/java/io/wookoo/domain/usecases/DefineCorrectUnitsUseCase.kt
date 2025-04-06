package io.wookoo.domain.usecases

import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherUnits
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@CoveredByTest
class DefineCorrectUnitsUseCase @Inject constructor(
    private val dataStore: IDataStoreRepo
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
                WeatherUnit.MS.apiValue -> WeatherUnit.MS
                else -> WeatherUnit.KMH
            }
        )
    }
}
