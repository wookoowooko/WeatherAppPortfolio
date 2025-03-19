package io.wookoo.data.repo

import android.util.Log
import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.database.daos.WeeklyWeatherDao
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.ReverseGeoCodingApi
import io.wookoo.domain.model.geocoding.GeocodingResponseModel
import io.wookoo.domain.model.reversegeocoding.ReverseGeocodingResponseModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.sync.ISynchronizer
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.EmptyResult
import io.wookoo.domain.utils.map
import io.wookoo.mappers.currentweather.asCurrentWeatherResponseModel
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
import javax.inject.Inject

private const val TAG = "MasterRepoImpl"

class MasterRepoImpl @Inject constructor(
    @GeoCodingApi private val geoCodingRemoteDataSource: IGeoCodingService,
    @ReverseGeoCodingApi private val reverseGeoCodingRemoteDataSource: IReverseGeoCodingService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val currentWeatherDao: CurrentWeatherDao,
    private val weeklyWeatherDao: WeeklyWeatherDao,
    private val synchronizer: ISynchronizer,
) : IMasterWeatherRepo {

    override fun currentWeather(geoNameId: Long): Flow<CurrentWeatherResponseModel> {
        Log.d(TAG, "currentWeather: $geoNameId")
        return currentWeatherDao.getCurrentWeather(geoNameId)
            .mapNotNull {
                Log.d(TAG, "currentWeather: $it")
                it?.asCurrentWeatherResponseModel()
            }
            .flowOn(ioDispatcher)
    }

    override fun weeklyWeatherForecastFlowFromDB(geoNameId: Long): Flow<WeeklyWeatherResponseModel> {
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

    override fun getAllCitiesCurrentWeather(): Flow<List<CurrentWeatherResponseModel>> {
        return currentWeatherDao.getAllCitiesCurrentWeather().mapNotNull {
            it.map { weather ->
                weather.asCurrentWeatherResponseModel()
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun syncWeeklyWeatherFromAPIAndSaveToCache(
        latitude: Double,
        longitude: Double,
        geoItemId: Long,
        cityName: String,
    ): EmptyResult<DataError> {
        Log.d(TAG, "syncWeeklyWeather")
        return synchronizer.synchronizeWeeklyWeather(
            latitude = latitude,
            longitude = longitude,
            geoItemId = geoItemId,
            cityName = cityName,
        )
    }

    override suspend fun synchronizeCurrentWeather(
        latitude: Double,
        longitude: Double,
        geoItemId: Long,
        countryName: String,
        cityName: String,
    ): AppResult<Unit, DataError> {
        Log.d(TAG, "syncCurrentWeather")
        return synchronizer.synchronizeCurrentWeather(
            latitude = latitude,
            longitude = longitude,
            geoItemId = geoItemId,
            countryName = countryName,
            cityName = cityName,

        )
    }

    override suspend fun getSearchedLocation(
        query: String,
        language: String,
    ): AppResult<GeocodingResponseModel, DataError.Remote> {
        return withContext(ioDispatcher) {
            geoCodingRemoteDataSource.getSearchedLocation(
                name = query,
                language = language
            ).map { dto ->
                Log.d(TAG, "getSearchedLocation: $dto")
                val b = dto.asGeocodingResponseModel()
                Log.d(TAG, "mapped: $b")
                b
            }
        }
    }

    override suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<ReverseGeocodingResponseModel, DataError.Remote> {
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

}
