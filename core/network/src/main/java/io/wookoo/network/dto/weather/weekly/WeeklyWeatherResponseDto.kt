package io.wookoo.network.dto.weather.weekly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyWeatherResponseDto(
    @SerialName("current")
    val currentShort: CurrentWeatherShortDto,
    @SerialName("daily")
    val week: WeeklyWeatherDto,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Long

)


