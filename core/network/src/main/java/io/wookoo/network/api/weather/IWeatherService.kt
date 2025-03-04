package io.wookoo.network.api.weather

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.weather.current.CurrentWeatherResponseDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto

interface IWeatherService {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
    ): AppResult<CurrentWeatherResponseDto, DataError.Remote>

    suspend fun getWeeklyWeather(
        latitude: Double,
        longitude: Double,
    ): AppResult<WeeklyWeatherResponseDto, DataError.Remote>
}
