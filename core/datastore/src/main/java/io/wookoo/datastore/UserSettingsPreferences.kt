package io.wookoo.datastore

import androidx.datastore.core.DataStore
import io.wookoo.domain.settings.UserLocationModel
import io.wookoo.domain.settings.UserSettingsModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsPreferences @Inject constructor(
    private val dataStore: DataStore<UserSettings>,
) {

    val userData = dataStore.data.map { settings ->
        UserSettingsModel(
            location = UserLocationModel(
                latitude = settings.location.latitude,
                longitude = settings.location.longitude
            ),
            isLocationChoose = settings.isLocationChoose
        )
    }

    suspend fun saveUserLocation(latitude: Double, longitude: Double) {
        dataStore.updateData { data ->
            data.toBuilder()
                .setLocation(
                    data.location.toBuilder()
                        .setLatitude(latitude)
                        .setLongitude(longitude)
                        .build()
                )
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
}
