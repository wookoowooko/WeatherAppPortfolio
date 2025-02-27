package io.wookoo.network.api

import io.wookoo.network.dto.CurrentWeatherResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface IRetrofit {

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: String = LATITUDE,
        @Query("longitude") longitude: String = LONGITUDE,
        @Query("current") current: String = CURRENT,
        @Query("hourly") hourly: String = HOURLY,
        @Query("forecast_days") forecastDays: Int = FORECAST_DAYS,
        @Query("timezone") timezone: String = TIMEZONE,
        @Query("timeformat") temperatureUnit: String = TIME_FORMAT,
        @Query("daily") timeFormat: String = DAILY,
    ): Response<CurrentWeatherResponseDto>

    private companion object {
        const val LATITUDE = "55.96056"
        const val LONGITUDE = "38.04556"
        const val CURRENT =
            "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m"
        const val HOURLY = "temperature_2m,weather_code,is_day"
        const val FORECAST_DAYS = 1
        const val TIMEZONE = "auto"
        const val TIME_FORMAT = "unixtime"
        const val DAILY = "sunrise,sunset,uv_index_max"
    }

}


