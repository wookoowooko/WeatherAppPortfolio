package io.wookoo.network.api

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.CurrentWeatherResponseDto

interface IWeatherService {
    suspend fun getCurrentWeather(): AppResult<CurrentWeatherResponseDto, DataError.Remote>
}