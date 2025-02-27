package io.wookoo.domain.model

data class CurrentDayModel(
    val time: Long,
    val temperature: Float,
    val relativeHumidity: Int,
    val feelsLike: Float,
    val isDay: Boolean,
    val precipitation: PrecipitationModel,
    val cloudCover: Int,
    val pressureMSL: Float,
    val wind: WindModel,
    val weatherStatus: Int,
)
