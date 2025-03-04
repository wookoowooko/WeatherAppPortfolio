package io.wookoo.domain.model.weather.current

data class DailyModel(
    val sunCycles: SunCyclesModel,
    val uvIndexMax: List<Float>,
)
