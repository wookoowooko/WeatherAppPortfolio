package io.wookoo.models.weather.weekly.additional

import io.wookoo.models.weather.current.additional.PrecipitationModel
import io.wookoo.models.weather.current.additional.SunCyclesModel
import io.wookoo.models.weather.current.additional.WindModel

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
    val precipitationProbabilityMax: List<Int>,
    val precipitationData: List<PrecipitationModel>,
    val windData: List<WindModel>,
)
