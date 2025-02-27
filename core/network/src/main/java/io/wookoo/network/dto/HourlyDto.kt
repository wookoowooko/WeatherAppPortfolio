package io.wookoo.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyDto(
    @SerialName("time")
    val time: List<Long>,

    @SerialName("temperature_2m")
    val temperature: List<Float>,

    @SerialName("weather_code")
    val weatherCode: List<Int>,

    @SerialName("is_day")
    val isDay: List<Int>,
)


