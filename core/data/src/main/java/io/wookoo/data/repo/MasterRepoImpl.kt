package io.wookoo.data.repo

import android.util.Log
import io.wookoo.data.mappers.currentweather.asCurrentWeatherEntity
import io.wookoo.data.mappers.currentweather.asCurrentWeatherResponseModel
import io.wookoo.data.mappers.currentweather.asDailyEntity
import io.wookoo.data.mappers.currentweather.asHourlyEntity
import io.wookoo.data.mappers.geocoding.asGeocodingResponseModel
import io.wookoo.data.mappers.geocoding.asReverseGeocodingResponseModel
import io.wookoo.data.mappers.weeklyweather.asWeeklyWeatherResponseModel
import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.database.dbo.GeoEntity
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.ReverseGeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.domain.model.geocoding.GeocodingResponseModel
import io.wookoo.domain.model.reversegeocoding.ReverseGeocodingResponseModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.EmptyResult
import io.wookoo.domain.utils.map
import io.wookoo.domain.utils.onError
import io.wookoo.domain.utils.onSuccess
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.reversegeocoding.IReverseGeoCodingService
import io.wookoo.network.api.weather.IWeatherService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "MasterRepoImpl"

class MasterRepoImpl @Inject constructor(
    @WeatherApi private val weatherRemoteDataSource: IWeatherService,
    @GeoCodingApi private val geoCodingRemoteDataSource: IGeoCodingService,
    @ReverseGeoCodingApi private val reverseGeoCodingRemoteDataSource: IReverseGeoCodingService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val weatherLocalDataSource: CurrentWeatherDao,
    private val dataStore: IDataStoreRepo,
) : IMasterWeatherRepo {

    override fun currentWeather(geoNameId: Long): Flow<CurrentWeatherResponseModel> {
        Log.d(TAG, "currentWeather: $geoNameId")
        return weatherLocalDataSource.getCurrentWeather(geoNameId)
            .mapNotNull {
                Log.d(TAG, "currentWeather: $it")

                it?.asCurrentWeatherResponseModel()
            }
            .flowOn(ioDispatcher)
    }


    override suspend fun syncCurrentWeather(
        latitude: Double,
        longitude: Double,
    ): EmptyResult<DataError> {

        val geoResult = reverseGeoCodingRemoteDataSource.getReversedSearchedLocation(
            latitude,
            longitude,
            language = "ru"
        ).onError { return AppResult.Error(it) }
            .onSuccess { geo ->
                geo.geonames?.firstOrNull()?.let { geoData ->
                    dataStore.saveGeoNameId(geoData.geoNameId)
                }
            }

        val weatherResult = weatherRemoteDataSource.getCurrentWeather(latitude, longitude)
            .onError { return AppResult.Error(it) }


        val remoteWeather = (weatherResult as AppResult.Success).data
        val geoData = (geoResult as AppResult.Success).data.geonames?.firstOrNull()


        val geo = GeoEntity(
            timezone = remoteWeather.timezone,
            geoNameId = geoData?.geoNameId ?: 0,
            countryName = geoData?.countryName ?: "",
            cityName = geoData?.name ?: "",
        )

        weatherLocalDataSource.insertFullWeather(
            geo = geo,
            current = remoteWeather.current.asCurrentWeatherEntity(),
            hourly = remoteWeather.hourly.asHourlyEntity(),
            daily = remoteWeather.daily.asDailyEntity()
        )

        return AppResult.Success(Unit)
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


//    override suspend fun observeActualCurrentWeather(
//        latitude: Double,
//        longitude: Double,
//    ): EmptyResult<DataError> {
//
//        val localWeather: WeatherEntity? = weatherLocalDataSource.getCurrentWeather().firstOrNull()
//
//        return if (localWeather != null) {
//
//            // Если записи в бд есть
//            // данные актуальны, возвращаем EmptyResult без ошибки
//            EmptyResult
//        } else {
//            reverseGeoCodingRemoteDataSource.getReversedSearchedLocation(
//                latitude,
//                longitude,
//                language = "ru"
//            ).fold(
//                onSuccess = {
//                    weatherRemoteDataSource.getCurrentWeather(latitude, longitude).fold(
//                        onSuccess = { remoteWeather ->
//                            weatherLocalDataSource.insertCurrentWeather(remoteWeather.asCurrentWeatherEntity())
//                            EmptyResult()  // Возвращаем EmptyResult, т.к. операция завершена успешно
//                        },
//                        onError = { error ->
//                            // Обработка ошибки получения данных с API
//                            EmptyResult(error)
//                        }
//                    )
//                },
//                onError = { error ->
//                    // Обработка ошибки геокодирования
//                    EmptyResult(error)
//                }
//            )
//        }
//    }


