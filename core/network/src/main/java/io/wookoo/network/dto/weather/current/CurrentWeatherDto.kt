package io.wookoo.network.dto.weather.current

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherDto(
    @SerialName("time")
    val time: Long,
    @SerialName("temperature_2m")
    val temperature: Double,
    @SerialName("relative_humidity_2m")
    val relativeHumidity: Int,
    @SerialName("apparent_temperature")
    val feelsLike: Double,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("precipitation")
    val precipitation: Double,
    @SerialName("rain")
    val rain: Double,
    @SerialName("showers")
    val showers: Double,
    @SerialName("snowfall")
    val snowfall: Double,
    @SerialName("cloud_cover")
    val cloudCover: Int,
    @SerialName("pressure_msl")
    val pressureMSL: Double,
    @SerialName("wind_direction_10m")
    val windDirection: Int,
    @SerialName("wind_speed_10m")
    val windSpeed: Double,
    @SerialName("wind_gusts_10m")
    val windGusts: Double,
    @SerialName("weather_code")
    val weatherCode: Int,
)
