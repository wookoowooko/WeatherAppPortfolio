package io.wookoo.data.repo

import io.wookoo.datastore.UserSettingsPreferences
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.settings.UserSettingsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepoImpl @Inject constructor(
    private val dataStore: UserSettingsPreferences,
) : IDataStoreRepo {

    override val userSettings: Flow<UserSettingsModel>
        get() = dataStore.userData

    override suspend fun saveUserLocation(latitude: Double, longitude: Double) {
        dataStore.saveUserLocation(latitude, longitude)
    }

    override suspend fun saveInitialLocationPicked(boolean: Boolean) {
        dataStore.saveInitialLocationPicked(boolean)
    }
}
