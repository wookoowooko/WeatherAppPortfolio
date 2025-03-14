package io.wookoo.data.repo

import io.wookoo.datastore.UserSettingsPreferences
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.settings.UserSettingsModel
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepoImpl @Inject constructor(
    private val dataStore: UserSettingsPreferences,
) : IDataStoreRepo {

    override val userSettings: Flow<UserSettingsModel>
        get() = dataStore.userData

    override suspend fun saveUserLocation(
        latitude: Double,
        longitude: Double,
    ): AppResult<Unit, DataError.Local> {
        return try {
            dataStore.saveUserLocation(latitude, longitude)
            AppResult.Success(Unit)
        } catch (e: UnsupportedOperationException) {
            println(e.message)
            AppResult.Error(DataError.Local.CAN_NOT_SAVE_DATA_TO_DATASTORE)
        }
    }

    override suspend fun saveGeoNameId(geoNameId: Long): AppResult<Unit, DataError.Local> {
        return try {
            dataStore.saveGeoItemId(geoNameId)
            AppResult.Success(Unit)
        } catch (e: UnsupportedOperationException) {
            println(e.message)
            AppResult.Error(DataError.Local.CAN_NOT_SAVE_DATA_TO_DATASTORE)
        }
    }


    override suspend fun saveInitialLocationPicked(boolean: Boolean): AppResult<Unit, DataError.Local> {
        return try {
            dataStore.saveInitialLocationPicked(boolean)
            AppResult.Success(Unit)
        } catch (e: UnsupportedOperationException) {
            println(e.message)
            AppResult.Error(DataError.Local.CAN_NOT_SAVE_DATA_TO_DATASTORE)
        }
    }
}
