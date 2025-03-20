package io.wookoo.network.dto.weather.weekly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyWeatherResponseDto(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("current") val currentShort: CurrentWeatherShortDto,
    @SerialName("daily") val week: WeeklyWeatherDto,
)


