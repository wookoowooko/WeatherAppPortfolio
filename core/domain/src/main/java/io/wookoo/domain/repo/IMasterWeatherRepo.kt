package io.wookoo.domain.repo

import io.wookoo.domain.model.geocoding.GeocodingResponseModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError

interface IMasterWeatherRepo {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
    ): AppResult<CurrentWeatherResponseModel, DataError.Remote>

    suspend fun getSearchedLocation(
        query: String,
        language: String,
    ): AppResult<GeocodingResponseModel, DataError.Remote>
}
