package io.wookoo.domain.model.weather.current.additional

data class CurrentDayModel(
    val time: Long,
    val temperature: Double,
    val relativeHumidity: Int,
    val feelsLike: Double,
    val isDay: Boolean,
    val precipitation: PrecipitationModel,
    val cloudCover: Int,
    val pressureMSL: Double,
    val wind: WindModel,
    val weatherStatus: Int,
)
