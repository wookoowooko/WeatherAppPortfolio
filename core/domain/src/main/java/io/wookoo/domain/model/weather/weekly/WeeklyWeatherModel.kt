package io.wookoo.domain.model.weather.weekly

import io.wookoo.domain.model.weather.current.PrecipitationModel
import io.wookoo.domain.model.weather.current.SunCyclesModel
import io.wookoo.domain.model.weather.current.WindModel

data class WeeklyWeatherModel(
    val cityName: String,
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
    val precipitationProbabilityMax: List<Double>,
    val precipitationData: List<PrecipitationModel>,
    val windData: List<WindModel>,
)
