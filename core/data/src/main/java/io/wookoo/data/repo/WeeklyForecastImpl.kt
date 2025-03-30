package io.wookoo.data.repo

import io.wookoo.database.daos.WeeklyWeatherDao
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.IWeeklyForecastRepo
import io.wookoo.domain.sync.Syncable
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.mappers.weeklyweather.asWeeklyWeatherEntity
import io.wookoo.mappers.weeklyweather.asWeeklyWeatherResponseModel
import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.weather.IForecastService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import java.sql.SQLException
import java.util.Locale
import javax.inject.Inject

class WeeklyForecastImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val weeklyWeatherDao: WeeklyWeatherDao,
    @WeatherApi private val forecastService: IForecastService,
    @GeoCodingApi private val geoCodingService: IGeoCodingService,
    private val dataStore: IDataStoreRepo,
) : IWeeklyForecastRepo, Syncable {

    override fun getWeeklyForecastByGeoItemId(geoNameId: Long): Flow<WeeklyWeatherDomainUI> {
        return weeklyWeatherDao.getWeeklyForecastByGeoItemId(geoNameId)
            .mapNotNull {
                it?.asWeeklyWeatherResponseModel()
            }.flowOn(ioDispatcher)
    }

    /**
     * Synchronizes the weekly weather forecast for a given location.
     *
     * @param geoItemId The unique identifier of the geographical location.
     * @return [AppResult] indicating success or failure of the synchronization.
     */
    override suspend fun sync(geoItemId: Long): AppResult<Unit, DataError> =
        withContext(ioDispatcher) {
            /**
             * Retrieves user settings and validates required preferences.
             */
            val settings = dataStore.userSettings.first()
            val temperatureUnit = settings.temperatureUnit
            val windSpeedUnit = settings.windSpeedUnit
            val precipitationUnit = settings.precipitationUnit

            if (temperatureUnit.isEmpty() || windSpeedUnit.isEmpty() || precipitationUnit.isEmpty()) {
                return@withContext AppResult.Error(DataError.Local.LOCAL_STORAGE_ERROR)
            }

            /**
             * Fetches geo-information for the given location.
             */
            val geoResult = geoCodingService.getInfoByGeoItemId(
                geoItemId,
                Locale.getDefault().language.lowercase()
            )
            if (geoResult is AppResult.Error) return@withContext AppResult.Error(geoResult.error)

            val geoInfo = (geoResult as AppResult.Success).data

            /**
             * Retrieves the weekly weather forecast for the given location.
             */
            val weatherResult = forecastService.getWeeklyWeather(
                latitude = geoInfo.latitude,
                longitude = geoInfo.longitude,
                temperatureUnit = temperatureUnit,
                windSpeedUnit = windSpeedUnit,
                precipitationUnit = precipitationUnit
            )
            if (weatherResult is AppResult.Error) return@withContext AppResult.Error(weatherResult.error)

            val weatherResponse = (weatherResult as AppResult.Success).data

            /**
             * Saves the fetched weekly weather data into the local database.
             */
            try {
                weeklyWeatherDao.insertWeeklyForecast(
                    weatherResponse.week.asWeeklyWeatherEntity(
                        isDay = weatherResponse.currentShort.isDay == 1,
                        geoNameId = geoItemId,
                        cityName = geoInfo.name,
                        utcOffsetSeconds = weatherResponse.utcOffsetSeconds
                    )
                )
                AppResult.Success(Unit)
            } catch (e: SQLException) {
                println(e)
                AppResult.Error(DataError.Local.DISK_FULL)
            }
        }
}
