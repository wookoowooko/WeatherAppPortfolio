package io.wookoo.data.repo

import io.wookoo.data.mappers.currentweather.asCurrentWeatherResponseModel
import io.wookoo.data.mappers.geocoding.asGeocodingResponseModel
import io.wookoo.data.mappers.geocoding.asReverseGeocodingResponseModel
import io.wookoo.data.mappers.weeklyweather.asWeeklyWeatherResponseModel
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.ReverseGeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.domain.model.geocoding.GeocodingResponseModel
import io.wookoo.domain.model.reversegeocoding.ReverseGeocodingResponseModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.map
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.reversegeocoding.IReverseGeoCodingService
import io.wookoo.network.api.weather.IWeatherService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MasterRepoImpl @Inject constructor(
    @WeatherApi private val weatherRemoteDataSource: IWeatherService,
    @GeoCodingApi private val geoCodingRemoteDataSource: IGeoCodingService,
    @ReverseGeoCodingApi private val reverseGeoCodingRemoteDataSource: IReverseGeoCodingService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,

) : IMasterWeatherRepo {
    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
    ): AppResult<CurrentWeatherResponseModel, DataError.Remote> {
        return withContext(ioDispatcher) {
            weatherRemoteDataSource.getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            )
                .map { dto ->
                    dto.asCurrentWeatherResponseModel()
                }
        }
    }

    override suspend fun getSearchedLocation(
        query: String,
        language: String,
    ): AppResult<GeocodingResponseModel, DataError.Remote> {
        return withContext(ioDispatcher) {
            geoCodingRemoteDataSource.getSearchedLocation(
                name = query,
                language = language
            ).map { dto ->
                dto.asGeocodingResponseModel()
            }
        }
    }

    override suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<ReverseGeocodingResponseModel, DataError.Remote> {
        return withContext(ioDispatcher) {
            reverseGeoCodingRemoteDataSource.getReversedSearchedLocation(
                latitude = latitude,
                longitude = longitude,
                language = language
            ).map { dto ->
                dto.asReverseGeocodingResponseModel()
            }
        }
    }

    override suspend fun getWeeklyWeather(
        latitude: Double,
        longitude: Double,
    ): AppResult<WeeklyWeatherResponseModel, DataError.Remote> {
        return withContext(ioDispatcher) {
            weatherRemoteDataSource.getWeeklyWeather(
                latitude = latitude,
                longitude = longitude
            ).map { dto ->
                dto.asWeeklyWeatherResponseModel()
            }
        }
    }
}
