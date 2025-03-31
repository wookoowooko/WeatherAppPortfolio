package io.wookoo.datastore

import androidx.datastore.core.DataStore
import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.models.settings.UserSettingsModel
import io.wookoo.models.units.WeatherUnit
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsPreferences @Inject constructor(
    private val dataStore: DataStore<UserSettings>,
) {
    @CoveredByTest
    val userData = dataStore.data.map { settings ->
        UserSettingsModel(
            isLocationChoose = settings.isLocationChoose,
            temperatureUnit = settings.temperatureUnit,
            windSpeedUnit = settings.windSpeedUnit,
            precipitationUnit = settings.precipitationUnit
        )
    }

    @CoveredByTest
    suspend fun setInitialWeatherUnits() {
        dataStore.updateData { data ->
            data.toBuilder()
                .setTemperatureUnit(WeatherUnit.CELSIUS.apiValue)
                .setPrecipitationUnit(WeatherUnit.MM.apiValue)
                .setWindSpeedUnit(WeatherUnit.KMH.apiValue)
                .build()
        }
    }

    @CoveredByTest
    suspend fun saveInitialLocationPicked(checked: Boolean) {
        dataStore.updateData { data ->
            data.toBuilder()
                .setIsLocationChoose(checked)
                .build()
        }
    }

    @CoveredByTest
    suspend fun updateTemperatureUnit(temperatureUnit: String) {
        dataStore.updateData { data ->
            data.toBuilder()
                .setTemperatureUnit(temperatureUnit)
                .build()
        }
    }

    @CoveredByTest
    suspend fun updateWindSpeedUnit(windSpeedUnit: String) {
        dataStore.updateData { data ->
            data.toBuilder()
                .setWindSpeedUnit(windSpeedUnit)
                .build()
        }
    }

    @CoveredByTest
    suspend fun updatePrecipitationUnit(precipitationUnit: String) {
        dataStore.updateData { data ->
            data.toBuilder()
                .setPrecipitationUnit(precipitationUnit)
                .build()
        }
    }
}
