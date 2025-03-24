package io.wookoo.domain.repo

import io.wookoo.domain.enums.UpdateIntent
import io.wookoo.domain.model.geocoding.GeocodingResponseModel
import io.wookoo.domain.model.geocoding.ReverseGeocodingResponseModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.EmptyResult
import kotlinx.coroutines.flow.Flow

interface IMasterWeatherRepo {

    suspend fun updateCurrentLocation(geoItemId: Long):AppResult<Unit, DataError>

    fun currentWeather(geoNameId: Long): Flow<CurrentWeatherResponseModel>

    fun weeklyWeatherForecastFlowFromDB(geoNameId: Long): Flow<WeeklyWeatherResponseModel>

    fun getCurrentWeatherIds(): Flow<List<Long>>

    fun getAllCitiesCurrentWeather(): Flow<List<CurrentWeatherResponseModel>>

    suspend fun syncWeeklyWeatherFromAPIAndSaveToCache(
        geoItemId: Long,
        updateIntent: UpdateIntent,
    ): EmptyResult<DataError>

    suspend fun synchronizeCurrentWeather(
        geoItemId: Long,
    ): AppResult<Unit, DataError>

    suspend fun searchLocationFromApiByQuery(
        query: String,
        language: String,
    ): AppResult<GeocodingResponseModel, DataError.Remote>

    suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<ReverseGeocodingResponseModel, DataError.Remote>

    suspend fun deleteWeatherWithDetailsByGeoId(geoItemId: Long): AppResult<Unit, DataError>
}
