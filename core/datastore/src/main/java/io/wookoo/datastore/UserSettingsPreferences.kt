package io.wookoo.datastore

import androidx.datastore.core.DataStore
import io.wookoo.domain.settings.UserSettingsModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsPreferences @Inject constructor(
    private val dataStore: DataStore<UserSettings>,
) {
    val userData = dataStore.data.map { settings ->
        UserSettingsModel(
            isLocationChoose = settings.isLocationChoose
        )
    }

    suspend fun saveInitialLocationPicked(checked: Boolean) {
        dataStore.updateData { data ->
            data.toBuilder()
                .setIsLocationChoose(checked)
                .build()
        }
    }
}
