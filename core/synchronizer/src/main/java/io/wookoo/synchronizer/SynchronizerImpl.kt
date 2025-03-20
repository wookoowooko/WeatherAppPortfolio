package io.wookoo.synchronizer

import android.util.Log
import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.database.daos.WeeklyWeatherDao
import io.wookoo.database.dbo.GeoEntity
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.domain.sync.ISynchronizer
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.mappers.currentweather.asCurrentWeatherEntity
import io.wookoo.mappers.currentweather.asDailyEntity
import io.wookoo.mappers.currentweather.asHourlyEntity
import io.wookoo.mappers.weeklyweather.asWeeklyWeatherEntity
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.weather.IWeatherService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
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

    override suspend fun synchronizeWeeklyWeather(
        geoItemId: Long,
    ): AppResult<Unit, DataError> {
        Log.d(TAG, "syncWeeklyWeather for geoItemId: $geoItemId")

        var result: AppResult<Unit, DataError>

        withContext(ioDispatcher) {
            try {
                // 1. Check data freshness
                val lastUpdate = weeklyWeatherDao.getLastUpdateForWeekly(geoItemId)
                val oneHourAgo = System.currentTimeMillis() - 60 * 60 * 1000

                if (lastUpdate > oneHourAgo) {
                    Log.d(TAG, "Weather data is fresh, skipping update")
                    result = AppResult.Success(Unit)
                    return@withContext
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
                val weatherResponse = (weatherResult as AppResult.Success).data

                // 4. Create and save entity
                weeklyWeatherDao.insertWeeklyWeather(
                    weatherResponse.week.asWeeklyWeatherEntity(
                        isDay = weatherResponse.currentShort.isDay == 1,
                        geoNameId = geoItemId,
                        longitude = weatherResponse.longitude,
                        latitude = weatherResponse.latitude,
                        cityName = geoInfo.name
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
        latitude: Double,
        longitude: Double,
        geoItemId: Long,
        countryName: String,
        cityName: String,
    ): AppResult<Unit, DataError> {
        Log.d(TAG, "syncCurrentWeather")

        return withContext(ioDispatcher) {
            val weatherDeferred = async {
                weatherRemoteDataSource.getCurrentWeather(latitude, longitude)
            }
            val weatherResult = weatherDeferred.await()
            when {
                weatherResult is AppResult.Success -> {

                    val remoteWeather = weatherResult.data

                    try {
                        currentWeatherDao.insertFullWeather(
                            geo = GeoEntity(
                                geoNameId = geoItemId,
                                countryName = countryName,
                                cityName = cityName
                            ),
                            current = remoteWeather.current.asCurrentWeatherEntity(
                                latitude = remoteWeather.latitude,
                                longitude = remoteWeather.longitude,
                            ),
                            hourly = remoteWeather.hourly.asHourlyEntity(),
                            daily = remoteWeather.daily.asDailyEntity()
                        )

                        AppResult.Success(Unit)
                    } catch (e: SQLException) {
                        Log.e(TAG, "Database error: $e")
                        AppResult.Error(DataError.Local.DISK_FULL)
                    }
                }

                else -> {
                    val error = when {
                        weatherResult is AppResult.Error -> weatherResult.error
                        else -> DataError.Remote.CANT_SYNC // Fallback, если что-то пошло не так
                    }
                    AppResult.Error(error)
                }
            }
        }
    }

    companion object {
        private const val TAG = "SynchronizerImpl"
    }
}
