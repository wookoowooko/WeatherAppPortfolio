package io.wookoo.domain.model.weather.current

data class DailyModel(
    val sunrise: List<Long>,
    val sunset: List<Long>,
    val uvIndexMax: List<Float>,
)
