package io.wookoo.network.dto.weather.current

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponseDto(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("current")
    val current: CurrentWeatherDto,
    @SerialName("hourly")
    val hourly: HourlyDto,
    @SerialName("daily")
    val daily: DailyDto

)
