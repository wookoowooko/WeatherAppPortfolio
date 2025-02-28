package io.wookoo.network.dto.weather.current

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CurrentWeatherDto(
    @SerialName("time")
    val time: Long,
    @SerialName("temperature_2m")
    val temperature: Float,
    @SerialName("relative_humidity_2m")
    val relativeHumidity: Int,
    @SerialName("apparent_temperature")
    val feelsLike: Float,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("precipitation")
    val precipitation: Float,
    @SerialName("rain")
    val rain: Float,
    @SerialName("showers")
    val showers: Float,
    @SerialName("snowfall")
    val snowfall: Float,
    @SerialName("cloud_cover")
    val cloudCover: Int,
    @SerialName("pressure_msl")
    val pressureMSL: Float,
    @SerialName("wind_direction_10m")
    val windDirection: Int,
    @SerialName("wind_speed_10m")
    val windSpeed: Float,
    @SerialName("wind_gusts_10m")
    val windGusts: Float,
    @SerialName("weather_code")
    val weatherCode: Int,
)
