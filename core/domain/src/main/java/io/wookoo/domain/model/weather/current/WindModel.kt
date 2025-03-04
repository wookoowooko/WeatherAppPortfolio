package io.wookoo.domain.model.weather.current

data class WindModel(
    val direction: Int,
    val speed: Double,
    val gust: Double,
)
