package io.wookoo.domain.repo

import io.wookoo.models.settings.UserSettingsModel
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import kotlinx.coroutines.flow.Flow

interface IDataStoreRepo {
    val userSettings: Flow<io.wookoo.models.settings.UserSettingsModel>
    suspend fun setInitialWeatherUnits(): AppResult<Unit, DataError.Local>
    suspend fun saveInitialLocationPicked(boolean: Boolean): AppResult<Unit, DataError.Local>
    suspend fun updateTemperatureUnit(temperatureUnit: String): AppResult<Unit, DataError.Local>
    suspend fun updateWindSpeedUnit(windSpeedUnit: String): AppResult<Unit, DataError.Local>
    suspend fun updatePrecipitationUnit(precipitationUnit: String): AppResult<Unit, DataError.Local>
}
