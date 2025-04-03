package io.wookoo.network.demo

import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.api.weather.IForecastService
import io.wookoo.network.dto.weather.current.CurrentWeatherResponseDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
@CoveredByTest
class DemoForecastImpl(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val demoAssetManager: IDemoAssetManager,
    private val networkJson: Json,
) : IForecastService {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        temperatureUnit: String,
        windSpeedUnit: String,
        precipitationUnit: String,
    ): AppResult<CurrentWeatherResponseDto, DataError.Remote> {
        return AppResult.Success(
            data = getDataFromJsonFile(CURRENT_FORECAST)
        )
    }

    override suspend fun getWeeklyWeather(
        latitude: Double,
        longitude: Double,
        temperatureUnit: String,
        windSpeedUnit: String,
        precipitationUnit: String,
    ): AppResult<WeeklyWeatherResponseDto, DataError.Remote> {
        return AppResult.Success(
            data = getDataFromJsonFile(WEEKLY_FORECAST)
        )
    }


    /**
     * Get data from the given JSON [fileName].
     */
    @OptIn(ExperimentalSerializationApi::class)
    private suspend inline fun <reified T> getDataFromJsonFile(fileName: String): T =
        withContext(ioDispatcher) {
            demoAssetManager.open(fileName).use { inputStream ->
                networkJson.decodeFromStream(inputStream)
            }
        }

    companion object {
        private const val CURRENT_FORECAST = "current_forecast.json"
        private const val WEEKLY_FORECAST = "weekly_forecast.json"
    }
}