package io.wookoo.datastore

import androidx.datastore.core.DataStore
import io.wookoo.domain.model.settings.UserSettingsModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsPreferences @Inject constructor(
    private val dataStore: DataStore<UserSettings>,
) {
    val userData = dataStore.data.map { settings ->
        UserSettingsModel(
            isLocationChoose = settings.isLocationChoose,
            temperatureUnit = settings.temperatureUnit,
            windSpeedUnit = settings.windSpeedUnit,
            precipitationUnit = settings.precipitationUnit
        )
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

}
