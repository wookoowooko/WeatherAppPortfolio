package io.wookoo.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import io.wookoo.models.settings.UserSettingsModel
import io.wookoo.models.units.WeatherUnit
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsPreferences @Inject constructor(
    private val dataStore: DataStore<UserSettings>,
) {
    val userData = dataStore.data.map { settings ->

        Log.d(TAG, ": $settings")
        io.wookoo.models.settings.UserSettingsModel(
            isLocationChoose = settings.isLocationChoose,
            temperatureUnit = settings.temperatureUnit,
            windSpeedUnit = settings.windSpeedUnit,
            precipitationUnit = settings.precipitationUnit
        )
    }

    suspend fun setInitialWeatherUnits() {
        dataStore.updateData { data ->
            data.toBuilder()
                .setTemperatureUnit(WeatherUnit.CELSIUS.apiValue)
                .setPrecipitationUnit(WeatherUnit.MM.apiValue)
                .setWindSpeedUnit(WeatherUnit.KMH.apiValue)
                .build()
        }
    }

    suspend fun saveInitialLocationPicked(checked: Boolean) {
        dataStore.updateData { data ->
            data.toBuilder()
                .setIsLocationChoose(checked)
                .build()
        }
    }

    suspend fun updateTemperatureUnit(temperatureUnit: String) {
        dataStore.updateData { data ->
            data.toBuilder()
                .setTemperatureUnit(temperatureUnit)
                .build()
        }
    }

    suspend fun updateWindSpeedUnit(windSpeedUnit: String) {
        dataStore.updateData { data ->
            data.toBuilder()
                .setWindSpeedUnit(windSpeedUnit)
                .build()
        }
    }

    suspend fun updatePrecipitationUnit(precipitationUnit: String) {
        dataStore.updateData { data ->
            data.toBuilder()
                .setPrecipitationUnit(precipitationUnit)
                .build()
        }
    }

    companion object {
        private const val TAG = "UserSettingsPreferences"
    }
}
