package io.wookoo.domain.repo

import io.wookoo.models.weather.current.CurrentWeatherDomain
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import kotlinx.coroutines.flow.Flow

interface ICurrentForecastRepo {
    suspend fun updateCurrentForecastLocation(geoItemId: Long): AppResult<Unit, DataError>

    fun getCurrentForecast(geoNameId: Long): Flow<io.wookoo.models.weather.current.CurrentWeatherDomain>

    fun getCurrentForecastGeoItemIds(): Flow<List<Long>>

    fun getAllCurrentForecastLocations(): Flow<List<io.wookoo.models.weather.current.CurrentWeatherDomain>>

    suspend fun deleteCurrentForecastEntryByGeoId(geoItemId: Long): AppResult<Unit, DataError>
}
