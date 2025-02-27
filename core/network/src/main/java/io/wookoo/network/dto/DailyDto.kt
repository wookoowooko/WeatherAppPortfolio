package io.wookoo.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyDto(
    @SerialName("sunrise")
    val sunrise: List<Long>,

    @SerialName("sunset")
    val sunset: List<Long>,

    @SerialName("uv_index_max")
    val uvIndexMax: List<Float>,
)