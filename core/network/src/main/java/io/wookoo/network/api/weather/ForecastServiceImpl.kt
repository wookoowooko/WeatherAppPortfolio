package io.wookoo.network.api.weather

import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.weather.current.CurrentWeatherResponseDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto
import io.wookoo.network.extensions.safeCall
import javax.inject.Inject
@CoveredByTest
class ForecastServiceImpl @Inject constructor(
    private val weatherApi: IForecastRetrofit,
) : IForecastService {
    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        temperatureUnit: String,
        windSpeedUnit: String,
        precipitationUnit: String
    ): AppResult<CurrentWeatherResponseDto, DataError.Remote> {
        return safeCall {
            weatherApi.getCurrentWeather(
                latitude = latitude,
                longitude = longitude,
                temperatureUnit = temperatureUnit,
                windSpeedUnit = windSpeedUnit,
                precipitationUnit = precipitationUnit,
            )
        }
    }

    override suspend fun getWeeklyWeather(
        latitude: Double,
        longitude: Double,
        temperatureUnit: String,
        windSpeedUnit: String,
        precipitationUnit: String
    ): AppResult<WeeklyWeatherResponseDto, DataError.Remote> {
        return safeCall {
            weatherApi.getWeeklyWeather(
                latitude = latitude,
                longitude = longitude,
                temperatureUnit = temperatureUnit,
                windSpeedUnit = windSpeedUnit,
                precipitationUnit = precipitationUnit
            )
        }
    }
}