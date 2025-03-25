package io.wookoo.domain.repo

import io.wookoo.domain.model.geocoding.GeocodingResponseDomainUi
import io.wookoo.domain.model.weather.current.CurrentWeatherDomain
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import kotlinx.coroutines.flow.Flow

interface IMasterWeatherRepo {

    suspend fun updateCurrentLocation(geoItemId: Long): AppResult<Unit, DataError>

    fun currentWeather(geoNameId: Long): Flow<CurrentWeatherDomain>

    fun weeklyWeatherForecastFlowFromDB(geoNameId: Long): Flow<WeeklyWeatherDomainUI>

    fun getCurrentWeatherIds(): Flow<List<Long>>

    fun getAllCitiesCurrentWeather(): Flow<List<CurrentWeatherDomain>>

    suspend fun searchLocationFromApiByQuery(
        query: String,
        language: String,
    ): AppResult<GeocodingResponseDomainUi, DataError.Remote>

    suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<GeocodingResponseDomainUi, DataError.Remote>

    suspend fun deleteWeatherWithDetailsByGeoId(geoItemId: Long): AppResult<Unit, DataError>
}
