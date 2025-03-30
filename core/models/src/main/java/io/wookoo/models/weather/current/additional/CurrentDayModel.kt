package io.wookoo.models.weather.current.additional

data class CurrentDayModel(
    val time: Long,
    val temperature: Double,
    val relativeHumidity: Int,
    val feelsLike: Double,
    val isDay: Boolean,
    val precipitation: io.wookoo.models.weather.current.additional.PrecipitationModel,
    val cloudCover: Int,
    val pressureMSL: Double,
    val wind: io.wookoo.models.weather.current.additional.WindModel,
    val weatherStatus: Int,
)
