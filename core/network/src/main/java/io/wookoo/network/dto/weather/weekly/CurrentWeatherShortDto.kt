package io.wookoo.network.dto.weather.weekly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherShortDto(
    @SerialName("is_day")
    val isDay: Int,
)