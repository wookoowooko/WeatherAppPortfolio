package io.wookoo.network.api.weather

import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.weather.current.CurrentWeatherResponseDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto
@CoveredByTest
interface IForecastService {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        temperatureUnit: String,
        windSpeedUnit: String,
        precipitationUnit: String
    ): AppResult<CurrentWeatherResponseDto, DataError.Remote>

    suspend fun getWeeklyWeather(
        latitude: Double,
        longitude: Double,
        temperatureUnit: String,
        windSpeedUnit: String,
        precipitationUnit: String
    ): AppResult<WeeklyWeatherResponseDto, DataError.Remote>
}
