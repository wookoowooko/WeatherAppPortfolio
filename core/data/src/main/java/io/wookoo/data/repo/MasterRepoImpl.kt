package io.wookoo.data.repo

import io.wookoo.data.di.dispatchers.AppDispatchers
import io.wookoo.data.di.dispatchers.Dispatcher
import io.wookoo.data.mappers.asCurrentWeatherResponseModel
import io.wookoo.data.mappers.asGeocodingResponseModel
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.domain.model.geocoding.GeocodingResponseModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.map
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.weather.ICurrentWeatherService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MasterRepoImpl @Inject constructor(
    @WeatherApi private val weatherRemoteDataSource: ICurrentWeatherService,
    @GeoCodingApi private val geoCodingRemoteDataSource: IGeoCodingService,
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
}
