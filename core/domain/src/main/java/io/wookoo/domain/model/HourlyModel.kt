package io.wookoo.domain.model

data class HourlyModel(
    val time: List<String>,
    val temperature: List<Float>,
    val weatherCode: List<Int>,
)
