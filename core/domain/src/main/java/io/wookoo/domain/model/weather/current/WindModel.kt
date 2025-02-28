package io.wookoo.domain.model.weather.current

data class WindModel(
    val direction: Int,
    val speed: Float,
    val gust: Float,
)
