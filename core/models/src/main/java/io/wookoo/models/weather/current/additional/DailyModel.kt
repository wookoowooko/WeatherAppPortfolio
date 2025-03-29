package io.wookoo.models.weather.current.additional

data class DailyModel(
    val sunCycles: io.wookoo.models.weather.current.additional.SunCyclesModel,
    val uvIndexMax: List<Float>,
)
