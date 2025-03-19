package io.wookoo.domain.repo

import io.wookoo.domain.model.geocoding.GeocodingResponseModel
import io.wookoo.domain.model.reversegeocoding.ReverseGeocodingResponseModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.EmptyResult
import kotlinx.coroutines.flow.Flow

interface IMasterWeatherRepo {

    fun currentWeather(geoNameId: Long): Flow<CurrentWeatherResponseModel>

    suspend fun syncCurrentWeather(
        latitude: Double,
        longitude: Double,
    ): EmptyResult<DataError>

    fun weeklyWeatherForecastFlowFromDB(geoNameId: Long): Flow<WeeklyWeatherResponseModel>

    fun getCurrentWeatherIds(): Flow<List<Long>>

    fun getAllCitiesCurrentWeather(): Flow<List<CurrentWeatherResponseModel>>

    suspend fun syncWeeklyWeatherFromAPIAndSaveToCache(
        latitude: Double,
        longitude: Double,
        geoItemId: Long
    ): EmptyResult<DataError>

    suspend fun getSearchedLocation(
        query: String,
        language: String,
    ): AppResult<GeocodingResponseModel, DataError.Remote>

    suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<ReverseGeocodingResponseModel, DataError.Remote>

    suspend fun fetchWeeklyWeatherForecastFromAPI(
        latitude: Double,
        longitude: Double,
    ): AppResult<WeeklyWeatherResponseModel, DataError.Remote>


}
