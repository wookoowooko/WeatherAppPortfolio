package io.wookoo.network.api.weather

import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.network.dto.weather.current.CurrentWeatherResponseDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

@CoveredByTest
interface IForecastRetrofit {

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = CURRENT,
        @Query("hourly") hourly: String = HOURLY,
        @Query("forecast_days") forecastDays: Int = FORECAST_DAYS,
        @Query("timezone") timezone: String = TIMEZONE,
        @Query("timeformat") timeFormat: String = TIME_FORMAT,
        @Query("daily") daily: String = DAILY,
        @Query("temperature_unit") temperatureUnit: String,
        @Query("wind_speed_unit") windSpeedUnit: String,
        @Query("precipitation_unit") precipitationUnit: String,
    ): Response<CurrentWeatherResponseDto>


    @GET("forecast")
    suspend fun getWeeklyWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = CURRENT_WEEKLY,
        @Query("daily") daily: String = DAILY_WEEK,
        @Query("timezone") timezone: String = TIMEZONE,
        @Query("timeformat") timeFormat: String = TIME_FORMAT,
        @Query("temperature_unit") temperatureUnit: String,
        @Query("wind_speed_unit") windSpeedUnit: String,
        @Query("precipitation_unit") precipitationUnit: String,
    ): Response<WeeklyWeatherResponseDto>

    private companion object {
        const val CURRENT_WEEKLY = "is_day"
        const val CURRENT =
            "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,pressure_msl,surface_pressure,wind_speed_10m,wind_direction_10m,wind_gusts_10m,uv_index"
        const val HOURLY = "temperature_2m,weather_code,is_day"
        const val FORECAST_DAYS = 1
        const val TIMEZONE = "auto"
        const val TIME_FORMAT = "unixtime"
        const val DAILY = "sunrise,sunset"
        const val DAILY_WEEK =
            "weather_code,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,daylight_duration,sunshine_duration,uv_index_max,precipitation_sum,rain_sum,showers_sum,snowfall_sum,precipitation_probability_max,wind_speed_10m_max,wind_gusts_10m_max,wind_direction_10m_dominant"
    }

}


