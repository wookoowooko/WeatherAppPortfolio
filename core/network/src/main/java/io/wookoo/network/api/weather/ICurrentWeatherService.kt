package io.wookoo.network.api.weather

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.weather.current.CurrentWeatherResponseDto

interface ICurrentWeatherService {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): AppResult<CurrentWeatherResponseDto, DataError.Remote>
}
