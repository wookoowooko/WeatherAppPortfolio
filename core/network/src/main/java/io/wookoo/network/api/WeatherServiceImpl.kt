package io.wookoo.network.api

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.CurrentWeatherResponseDto
import io.wookoo.network.extensions.safeCall
import javax.inject.Inject

class WeatherServiceImpl @Inject constructor(
    private val api: IRetrofit,
) : IWeatherService {
    override suspend fun getCurrentWeather(): AppResult<CurrentWeatherResponseDto, DataError.Remote> {
        return safeCall { api.getCurrentWeather() }
    }
}