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


    suspend fun getSearchedLocation(
        query: String,
        language: String,
    ): AppResult<GeocodingResponseModel, DataError.Remote>

    suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<ReverseGeocodingResponseModel, DataError.Remote>

    suspend fun getWeeklyWeather(
        latitude: Double,
        longitude: Double,
    ): AppResult<WeeklyWeatherResponseModel, DataError.Remote>

}
