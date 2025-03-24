package io.wookoo.synchronizer

import android.util.Log
import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.database.daos.WeeklyWeatherDao
import io.wookoo.database.dbo.GeoEntity
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.domain.enums.UpdateIntent
import io.wookoo.domain.sync.ISynchronizer
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.mappers.currentweather.asCurrentWeatherEntity
import io.wookoo.mappers.currentweather.asDailyEntity
import io.wookoo.mappers.currentweather.asHourlyEntity
import io.wookoo.mappers.weeklyweather.asWeeklyWeatherEntity
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.weather.IWeatherService
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.SQLException
import javax.inject.Inject

class SynchronizerImpl @Inject constructor(
    private val weeklyWeatherDao: WeeklyWeatherDao,
    private val currentWeatherDao: CurrentWeatherDao,
    @WeatherApi private val weatherRemoteDataSource: IWeatherService,
    @GeoCodingApi private val geoCodingService: IGeoCodingService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ISynchronizer {

    override suspend fun syncWeeklyWeatherFromAPIAndSaveToCache(
        geoItemId: Long,
        updateIntent: UpdateIntent,
    ): AppResult<Unit, DataError> {
        Log.d(TAG, "syncWeeklyWeather for geoItemId: $geoItemId")

        var result: AppResult<Unit, DataError>

        withContext(ioDispatcher) {
            try {
                if (updateIntent == UpdateIntent.FROM_USER) {
                    // 1. Check data freshness
                    val lastUpdate = weeklyWeatherDao.getLastUpdateForWeekly(geoItemId)
                    val oneHourAgo = System.currentTimeMillis() - 60 * 60 * 1000

                    if (lastUpdate > oneHourAgo) {
                        Log.d(TAG, "Weather data is fresh, skipping update")
                        result = AppResult.Success(Unit)
                        return@withContext
                    }
                }

                // 2. Get geo information
                val geoResult = geoCodingService.getInfoByGeoItemId(geoItemId, language = "ru")
                if (geoResult is AppResult.Error) {
                    result = AppResult.Error(geoResult.error)
                    return@withContext
                }
                val geoInfo = (geoResult as AppResult.Success).data

                // 3. Get weather data
                val weatherResult = weatherRemoteDataSource.getWeeklyWeather(
                    geoInfo.latitude,
                    geoInfo.longitude
                )
                if (weatherResult is AppResult.Error) {
                    result = AppResult.Error(weatherResult.error)
                    return@withContext
                }
                val weatherResponse: WeeklyWeatherResponseDto =
                    (weatherResult as AppResult.Success).data

                // 4. Create and save entity
                weeklyWeatherDao.insertWeeklyWeather(
                    weatherResponse.week.asWeeklyWeatherEntity(
                        isDay = weatherResponse.currentShort.isDay == 1,
                        geoNameId = geoItemId,
                        cityName = geoInfo.name,
                        utcOffsetSeconds = weatherResponse.utcOffsetSeconds
                    )
                )

                result = AppResult.Success(Unit)
            } catch (e: SQLException) {
                Log.e(TAG, "Database error: $e")
                result = AppResult.Error(DataError.Local.DISK_FULL)
            }
        }

        return result
    }

    override suspend fun synchronizeCurrentWeather(
        geoItemId: Long,
    ): AppResult<Unit, DataError> {
        Log.d(TAG, "syncCurrentWeather")

        var result: AppResult<Unit, DataError>

        withContext(ioDispatcher) {
            try {
                // 1. Get geo information
                val geoResult = geoCodingService.getInfoByGeoItemId(geoItemId, language = "ru")
                if (geoResult is AppResult.Error) {
                    result = AppResult.Error(geoResult.error)
                    return@withContext
                }
                val geoInfo = (geoResult as AppResult.Success).data

                // 2. Get weather data
                val weatherResult = weatherRemoteDataSource.getCurrentWeather(
                    geoInfo.latitude,
                    geoInfo.longitude
                )
                if (weatherResult is AppResult.Error) {
                    result = AppResult.Error(weatherResult.error)
                    return@withContext
                }
                val weatherResponse = (weatherResult as AppResult.Success).data

                currentWeatherDao.insertFullWeather(
                    geo = GeoEntity(
                        geoNameId = geoItemId,
                        countryName = geoInfo.country.orEmpty(),
                        cityName = geoInfo.name,
                        utcOffsetSeconds = weatherResponse.utcOffsetSeconds,
                    ),
                    current = weatherResponse.current.asCurrentWeatherEntity(),
                    hourly = weatherResponse.hourly.asHourlyEntity(),
                    daily = weatherResponse.daily.asDailyEntity()
                )
                result = AppResult.Success(Unit)
            } catch (e: SQLException) {
                Log.e(TAG, "Database error: $e")
                result = AppResult.Error(DataError.Local.DISK_FULL)
            }
        }
        return result
    }

    companion object {
        private const val TAG = "SynchronizerImpl"
    }
}
