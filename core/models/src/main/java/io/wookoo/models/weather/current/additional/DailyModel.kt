package io.wookoo.models.weather.current.additional

data class DailyModel(
    val sunCycles: SunCyclesModel,
    val uvIndexMax: List<Float>
)
