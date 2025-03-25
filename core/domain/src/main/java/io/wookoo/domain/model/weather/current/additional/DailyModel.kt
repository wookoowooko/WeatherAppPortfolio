package io.wookoo.domain.model.weather.current.additional

data class DailyModel(
    val sunCycles: SunCyclesModel,
    val uvIndexMax: List<Float>,
)
