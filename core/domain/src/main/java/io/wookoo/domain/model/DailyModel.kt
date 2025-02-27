package io.wookoo.domain.model

data class DailyModel(
    val sunrise: List<Long>,
    val sunset: List<Long>,
    val uvIndexMax: List<Float>,
)
