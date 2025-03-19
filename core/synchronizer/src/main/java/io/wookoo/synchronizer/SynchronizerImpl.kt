package io.wookoo.synchronizer

import android.util.Log
import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.database.daos.WeeklyWeatherDao
import io.wookoo.database.dbo.GeoEntity
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.ReverseGeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.domain.sync.ISynchronizer
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.mappers.currentweather.asCurrentWeatherEntity
import io.wookoo.mappers.currentweather.asDailyEntity
import io.wookoo.mappers.currentweather.asHourlyEntity
import io.wookoo.mappers.weeklyweather.asWeeklyWeatherEntity
import io.wookoo.network.api.reversegeocoding.IReverseGeoCodingService
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
    @ReverseGeoCodingApi private val reverseGeoCodingRemoteDataSource: IReverseGeoCodingService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ISynchronizer {

    override suspend fun synchronizeWeeklyWeather(
        latitude: Double,
        longitude: Double,
        geoItemId: Long,
        cityName: String,
    ): AppResult<Unit, DataError> {
        Log.d(TAG, "syncWeeklyWeather")

        return withContext(ioDispatcher) {
            val lastUpdate = weeklyWeatherDao.getLastUpdateForWeekly(geoItemId)
            val oneHourAgo = System.currentTimeMillis() - 60 * 60 * 1000

            if (lastUpdate > oneHourAgo) {
                Log.d(TAG, "Weather data is fresh, skipping update")
                return@withContext AppResult.Success(Unit)
            }

//            val geoDeferred = async {
//                reverseGeoCodingRemoteDataSource.getReversedSearchedLocation(
//                    latitude,
//                    longitude,
//                    language = "ru"
//                )
//            }
            val weatherDeferred = async {
                weatherRemoteDataSource.getWeeklyWeather(latitude, longitude)
            }

//            val geoResult = geoDeferred.await()
            val weatherResult = weatherDeferred.await()

            when {
//                geoResult is AppResult.Success &&
                        weatherResult is AppResult.Success -> {



//                    val geoData = geoResult.data.geonames?.firstOrNull()
                    val remoteWeather = weatherResult.data

                    val weeklyWeatherEntity = remoteWeather.week.asWeeklyWeatherEntity(
                        isDay = remoteWeather.currentShort.isDay == 1,
                        geoNameId = geoItemId,
                        longitude = remoteWeather.longitude,
                        latitude = remoteWeather.latitude,
//                        cityName = geoData?.name.orEmpty()
                        cityName = cityName
                    )

                    try {
                        weeklyWeatherDao.insertWeeklyWeather(weeklyWeatherEntity)
                        AppResult.Success(Unit)
                    } catch (e: SQLException) {
                        Log.e(TAG, "Database error: $e")
                        AppResult.Error(DataError.Local.DISK_FULL)
                    }
                }

                else -> {
                    val error = when {
//                        geoResult is AppResult.Error -> geoResult.error
                        weatherResult is AppResult.Error -> weatherResult.error
                        else -> DataError.Remote.CANT_SYNC // Fallback, если что-то пошло не так
                    }
                    AppResult.Error(error)
                }
            }
        }
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
