package io.wookoo.domain.repo

import io.wookoo.domain.settings.UserSettingsModel
import kotlinx.coroutines.flow.Flow

interface IDataStoreRepo {
    val userSettings: Flow<UserSettingsModel>
    suspend fun saveUserLocation(latitude: Double, longitude: Double)
    suspend fun saveInitialLocationPicked(boolean: Boolean)
}
