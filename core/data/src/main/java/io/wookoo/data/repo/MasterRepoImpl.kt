package io.wookoo.data.repo

import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.database.daos.WeeklyWeatherDao
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.ReverseGeoCodingApi
import io.wookoo.domain.model.geocoding.GeocodingResponseDomainUi
import io.wookoo.domain.model.weather.current.CurrentWeatherDomain
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.map
import io.wookoo.mappers.currentweather.FromDatabaseToUi.asCurrentWeatherDomainUi
import io.wookoo.mappers.geocoding.asGeocodingResponseModel
import io.wookoo.mappers.geocoding.asReverseGeocodingResponseModel
import io.wookoo.mappers.weeklyweather.asWeeklyWeatherResponseModel
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.reversegeocoding.IReverseGeoCodingService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import java.sql.SQLException
import javax.inject.Inject

class MasterRepoImpl @Inject constructor(
    @GeoCodingApi private val geoCodingRemoteDataSource: IGeoCodingService,
    @ReverseGeoCodingApi private val reverseGeoCodingRemoteDataSource: IReverseGeoCodingService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val currentWeatherDao: CurrentWeatherDao,
    private val weeklyWeatherDao: WeeklyWeatherDao,
) : IMasterWeatherRepo {

    override suspend fun updateCurrentLocation(geoItemId: Long): AppResult<Unit, DataError> {
        return withContext(ioDispatcher) {
            try {
                currentWeatherDao.updateCurrentLocation(geoItemId)
                AppResult.Success(Unit)
            } catch (e: SQLException) {
                println(e)
                AppResult.Error(DataError.Local.DISK_FULL)
            }
        }
    }

    override fun currentWeather(geoNameId: Long): Flow<CurrentWeatherDomain> {
        return currentWeatherDao.getCurrentWeather(geoNameId)
            .mapNotNull {
                it?.asCurrentWeatherDomainUi()
            }
            .flowOn(ioDispatcher)
    }

    override fun weeklyWeatherForecastFlowFromDB(geoNameId: Long): Flow<WeeklyWeatherDomainUI> {
        return weeklyWeatherDao.getWeeklyWeatherByGeoItemId(geoNameId)
            .mapNotNull {
                it?.asWeeklyWeatherResponseModel()
            }.flowOn(ioDispatcher)
    }

    override fun getCurrentWeatherIds(): Flow<List<Long>> {
        return currentWeatherDao.getCurrentWeatherIds()
            .filterNotNull()
            .flowOn(ioDispatcher)
    }

    override fun getAllCitiesCurrentWeather(): Flow<List<CurrentWeatherDomain>> {
        return currentWeatherDao.getAllCitiesCurrentWeather().mapNotNull {
            it.map { weather ->
                weather.asCurrentWeatherDomainUi()
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun searchLocationFromApiByQuery(
        query: String,
        language: String,
    ): AppResult<GeocodingResponseDomainUi, DataError.Remote> {
        return withContext(ioDispatcher) {
            geoCodingRemoteDataSource.searchLocation(
                name = query,
                language = language
            ).map { dto ->
                dto.asGeocodingResponseModel()
            }
        }
    }

    override suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<GeocodingResponseDomainUi, DataError.Remote> {
        return withContext(ioDispatcher) {
            reverseGeoCodingRemoteDataSource.getReversedSearchedLocation(
                latitude = latitude,
                longitude = longitude,
                language = language
            ).map { dto ->
                dto.asReverseGeocodingResponseModel()
            }
        }
    }

    override suspend fun deleteWeatherWithDetailsByGeoId(geoItemId: Long): AppResult<Unit, DataError> {
        return withContext(ioDispatcher) {
            try {
                currentWeatherDao.deleteWeatherWithDetailsByGeoId(geoItemId)
                AppResult.Success(Unit)
            } catch (e: SQLException) {
                println(e)
                AppResult.Error(DataError.Local.CANT_DELETE_DATA)
            }
        }
    }
}
