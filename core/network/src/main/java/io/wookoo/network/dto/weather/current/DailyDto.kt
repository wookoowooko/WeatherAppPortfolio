package io.wookoo.network.dto.weather.current

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyDto(
    @SerialName("sunrise")
    val sunrise: List<Long>,

    @SerialName("sunset")
    val sunset: List<Long>
)