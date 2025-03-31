package io.wookoo.data.repo

import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.database.dbo.GeoEntity
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.sync.Syncable
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.mappers.currentweather.FromApiToDatabase.asCurrentWeatherEntity
import io.wookoo.mappers.currentweather.FromApiToDatabase.asDailyEntity
import io.wookoo.mappers.currentweather.FromApiToDatabase.asHourlyEntity
import io.wookoo.mappers.currentweather.FromDatabaseToUi.asCurrentWeatherDomainUi
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.weather.IForecastService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import java.sql.SQLException
import java.util.Locale
import javax.inject.Inject

class CurrentForecastImpl @Inject constructor(
    private val currentWeatherDao: CurrentWeatherDao,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @WeatherApi private val forecastService: IForecastService,
    @GeoCodingApi private val geoCodingService: IGeoCodingService,
    private val dataStore: IDataStoreRepo,
) : ICurrentForecastRepo, Syncable {

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

    /**
     * Synchronizes weather data for a given location.
     *
     * @param geoItemId The unique identifier of the geographical location.
     * @return [AppResult] indicating success or failure of the synchronization.
     */
    override suspend fun sync(geoItemId: Long): AppResult<Unit, DataError> {
        var result: AppResult<Unit, DataError>
        withContext(ioDispatcher) {
            /**
             * Retrieves user settings and validates required preferences.
             */
            val settings = dataStore.userSettings.first()
            val temperatureUnit = settings.temperatureUnit
            val windSpeedUnit = settings.windSpeedUnit
            val precipitationUnit = settings.precipitationUnit

            if (temperatureUnit.isEmpty() || windSpeedUnit.isEmpty() || precipitationUnit.isEmpty()) {
                result = AppResult.Error(DataError.Local.LOCAL_STORAGE_ERROR)
                return@withContext
            }

            try {
                /**
                 * Fetches geo-information for the given location.
                 */
                val geoResult = geoCodingService.getInfoByGeoItemId(
                    geoItemId = geoItemId,
                    language = Locale.getDefault().language.lowercase()
                )
                if (geoResult is AppResult.Error) {
                    result = AppResult.Error(geoResult.error)
                    return@withContext
                }
                val geoInfo = (geoResult as AppResult.Success).data

                /**
                 * Retrieves the latest weather data for the given location.
                 */
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

                /**
                 * Saves the fetched weather data into the local database.
                 */
                currentWeatherDao.insertCurrentForecastWithDetails(
                    geo = GeoEntity(
                        geoItemId = geoItemId,
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
                println(e)
                result = AppResult.Error(DataError.Local.DISK_FULL)
            }
        }
        return result
    }
}
