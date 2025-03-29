package io.wookoo.data.repo

import io.wookoo.datastore.UserSettingsPreferences
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.model.settings.UserSettingsModel
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

class DataStoreImpl @Inject constructor(
    private val dataStore: UserSettingsPreferences,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : IDataStoreRepo {

    override val userSettings: Flow<UserSettingsModel>
        get() = dataStore.userData

    override suspend fun setInitialWeatherUnits(): AppResult<Unit, DataError.Local> =
        executeDatastoreOperation { dataStore.setInitialWeatherUnits() }

    override suspend fun saveInitialLocationPicked(boolean: Boolean) = executeDatastoreOperation {
        dataStore.saveInitialLocationPicked(boolean)
    }

    override suspend fun updateTemperatureUnit(temperatureUnit: String) =
        executeDatastoreOperation {
            dataStore.updateTemperatureUnit(temperatureUnit)
        }

    override suspend fun updateWindSpeedUnit(windSpeedUnit: String) = executeDatastoreOperation {
        dataStore.updateWindSpeedUnit(windSpeedUnit)
    }

    override suspend fun updatePrecipitationUnit(precipitationUnit: String) =
        executeDatastoreOperation {
            dataStore.updatePrecipitationUnit(precipitationUnit)
        }

    private suspend fun DataStoreImpl.executeDatastoreOperation(
        operation: suspend () -> Unit,
    ): AppResult<Unit, DataError.Local> {
        return try {
            withContext(ioDispatcher) {
                operation()
                AppResult.Success(Unit)
            }
        } catch (e: IOException) {
            println(e.message)
            AppResult.Error(DataError.Local.CAN_NOT_SAVE_DATA_TO_DATASTORE)
        }
    }
}
