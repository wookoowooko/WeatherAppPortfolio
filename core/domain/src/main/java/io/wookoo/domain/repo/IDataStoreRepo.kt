package io.wookoo.domain.repo

import io.wookoo.domain.settings.UserSettingsModel
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import kotlinx.coroutines.flow.Flow

interface IDataStoreRepo {
    val userSettings: Flow<UserSettingsModel>

    suspend fun saveUserLocation(
        latitude: Double,
        longitude: Double,
    ): AppResult<Unit, DataError.Local>

    suspend fun saveInitialLocationPicked(boolean: Boolean): AppResult<Unit, DataError.Local>
}
