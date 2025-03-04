package io.wookoo.domain.model.weekly

import io.wookoo.domain.model.weather.current.SunCyclesModel
import io.wookoo.domain.model.weather.current.WindModel

data class WeeklyWeatherModel(
    val time: List<Long>,
    val weatherCode: List<Int>,
    val tempMax: List<Double>,
    val tempMin: List<Double>,
    val apparentTempMax: List<Double>,
    val apparentTempMin: List<Double>,
    val sunCycles: SunCyclesModel,
    val dayLightDuration: List<Double>,
    val sunshineDuration: List<Double>,
    val uvIndexMax: List<Double>,
    val precipitationSum: List<Double>,
    val rainSum: List<Double>,
    val showersSum: List<Double>,
    val snowfallSum: List<Double>,
    val precipitationProbabilityMax: List<Double>,
    val windData: List<WindModel>,
)
