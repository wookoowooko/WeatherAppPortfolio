package io.wookoo.network.api.weather

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.weather.current.CurrentWeatherResponseDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto
import io.wookoo.network.extensions.safeCall
import javax.inject.Inject

class WeatherServiceImpl @Inject constructor(
    private val weatherApi: IWeatherRetrofit,
) : IWeatherService {
    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
    ): AppResult<CurrentWeatherResponseDto, DataError.Remote> {
        return safeCall {
            weatherApi.getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            )
        }
    }

    override suspend fun getWeeklyWeather(
        latitude: Double,
        longitude: Double,
    ): AppResult<WeeklyWeatherResponseDto, DataError.Remote> {
        return safeCall {
            weatherApi.getWeeklyWeather(
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}