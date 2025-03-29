package io.wookoo.synchronizer

import android.util.Log
import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.database.daos.WeeklyWeatherDao
import io.wookoo.database.dbo.GeoEntity
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.models.settings.UserSettingsModel
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.sync.ISynchronizer
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.mappers.currentweather.FromApiToDatabase.asCurrentWeatherEntity
import io.wookoo.mappers.currentweather.FromApiToDatabase.asDailyEntity
import io.wookoo.mappers.currentweather.FromApiToDatabase.asHourlyEntity
import io.wookoo.mappers.weeklyweather.asWeeklyWeatherEntity
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.weather.IForecastService
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.sql.SQLException
import java.util.Locale
import javax.inject.Inject

class SynchronizerImpl @Inject constructor(
    private val weeklyWeatherDao: WeeklyWeatherDao,
    private val currentWeatherDao: CurrentWeatherDao,
    @WeatherApi private val forecastService: IForecastService,
    @GeoCodingApi private val geoCodingService: IGeoCodingService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    dataStore: IDataStoreRepo,
) : ISynchronizer {

    private val settings = dataStore.userSettings
    private val language = Locale.getDefault().language.lowercase()

    override suspend fun syncWeeklyWeatherFromAPIAndSaveToCache(
        geoItemId: Long,
    ): AppResult<Unit, DataError> {
        Log.d(TAG, "syncWeeklyWeather for geoItemId: $geoItemId")

        var result: AppResult<Unit, DataError>

        withContext(ioDispatcher) {
            // 1. checkPrefs
            val settings: io.wookoo.models.settings.UserSettingsModel = settings.first()
            val temperatureUnit = settings.temperatureUnit
            val windSpeedUnit = settings.windSpeedUnit
            val precipitationUnit = settings.precipitationUnit

            if (temperatureUnit.isEmpty() || windSpeedUnit.isEmpty() || precipitationUnit.isEmpty()) {
                // todo do new exception
                result = AppResult.Error(DataError.Local.UNKNOWN)
                return@withContext
            }

            try {
                // 2. Get geo information
                val geoResult = geoCodingService.getInfoByGeoItemId(
                    geoItemId = geoItemId,
                    language = language
                )
                if (geoResult is AppResult.Error) {
                    result = AppResult.Error(geoResult.error)
                    return@withContext
                }
                val geoInfo = (geoResult as AppResult.Success).data

                // 3. Get weather data
                val weatherResult = forecastService.getWeeklyWeather(
                    latitude = geoInfo.latitude,
                    longitude = geoInfo.longitude,
                    temperatureUnit = temperatureUnit,
                    windSpeedUnit = windSpeedUnit,
                    precipitationUnit = precipitationUnit
                )
                if (weatherResult is AppResult.Error) {
                    result = AppResult.Error(weatherResult.error)
                    return@withContext
                }
                val weatherResponse: WeeklyWeatherResponseDto =
                    (weatherResult as AppResult.Success).data

                // 4. Create and save entity
                weeklyWeatherDao.insertWeeklyForecast(
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

    override suspend fun synchronizeCurrentWeatherFromAPIAndSaveToCache(
        geoItemId: Long,
    ): AppResult<Unit, DataError> {
        Log.d(TAG, "syncCurrentWeather")

        var result: AppResult<Unit, DataError>

        withContext(ioDispatcher) {
            // 1. checkPrefs
            val settings: io.wookoo.models.settings.UserSettingsModel = settings.first()
            val temperatureUnit = settings.temperatureUnit
            val windSpeedUnit = settings.windSpeedUnit
            val precipitationUnit = settings.precipitationUnit

            if (temperatureUnit.isEmpty() || windSpeedUnit.isEmpty() || precipitationUnit.isEmpty()) {
                // todo do new exception
                result = AppResult.Error(DataError.Local.UNKNOWN)
                return@withContext
            }

            try {
                // 2. Get geo information
                val geoResult = geoCodingService.getInfoByGeoItemId(
                    geoItemId = geoItemId,
                    language = language
                )
                if (geoResult is AppResult.Error) {
                    result = AppResult.Error(geoResult.error)
                    return@withContext
                }
                val geoInfo = (geoResult as AppResult.Success).data

                // 2. Get weather data
                val weatherResult = forecastService.getCurrentWeather(
                    latitude = geoInfo.latitude,
                    longitude = geoInfo.longitude,
                    temperatureUnit = temperatureUnit,
                    windSpeedUnit = windSpeedUnit,
                    precipitationUnit = precipitationUnit,
                )
                if (weatherResult is AppResult.Error) {
                    result = AppResult.Error(weatherResult.error)
                    return@withContext
                }
                val weatherResponse = (weatherResult as AppResult.Success).data

                currentWeatherDao.insertCurrentForecastWithDetails(
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
