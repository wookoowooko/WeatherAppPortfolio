package io.wookoo.network.dto.weather.current

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponseDto(
    @SerialName("current")
    val current: CurrentWeatherDto,
    @SerialName("hourly")
    val hourly: HourlyDto,
    @SerialName("daily")
    val daily: DailyDto,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Long,
)
