package io.wookoo.data.repo

import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.models.weather.current.CurrentWeatherDomain
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.mappers.currentweather.FromDatabaseToUi.asCurrentWeatherDomainUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import java.sql.SQLException
import javax.inject.Inject

class CurrentForecastImpl @Inject constructor(
    private val currentWeatherDao: CurrentWeatherDao,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ICurrentForecastRepo {

    override suspend fun deleteCurrentForecastEntryByGeoId(geoItemId: Long): AppResult<Unit, DataError> {
        return withContext(ioDispatcher) {
            try {
                currentWeatherDao.deleteCurrentForecastEntryByGeoId(geoItemId)
                AppResult.Success(Unit)
            } catch (e: SQLException) {
                println(e)
                AppResult.Error(DataError.Local.CANT_DELETE_DATA)
            }
        }
    }

    override fun getCurrentForecast(geoNameId: Long): Flow<io.wookoo.models.weather.current.CurrentWeatherDomain> {
        return currentWeatherDao.getCurrentForecast(geoNameId)
            .mapNotNull {
                it?.asCurrentWeatherDomainUi()
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun updateCurrentForecastLocation(geoItemId: Long): AppResult<Unit, DataError> {
        return withContext(ioDispatcher) {
            try {
                currentWeatherDao.updateCurrentForecastLocation(geoItemId)
                AppResult.Success(Unit)
            } catch (e: SQLException) {
                println(e)
                AppResult.Error(DataError.Local.DISK_FULL)
            }
        }
    }

    override fun getCurrentForecastGeoItemIds(): Flow<List<Long>> {
        return currentWeatherDao.getCurrentForecastGeoItemIds()
            .filterNotNull()
            .flowOn(ioDispatcher)
    }

    override fun getAllCurrentForecastLocations(): Flow<List<io.wookoo.models.weather.current.CurrentWeatherDomain>> {
        return currentWeatherDao.getAllCurrentForecastLocations().mapNotNull {
            it.map { weather ->
                weather.asCurrentWeatherDomainUi()
            }
        }.flowOn(ioDispatcher)
    }
}
