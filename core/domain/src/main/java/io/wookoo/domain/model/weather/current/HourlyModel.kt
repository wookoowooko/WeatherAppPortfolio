package io.wookoo.domain.model.weather.current

data class HourlyModel(
    val time: List<Long>,
    val temperature: List<Float>,
    val weatherCode: List<Int>,
    val isDay: List<Boolean>,
)
