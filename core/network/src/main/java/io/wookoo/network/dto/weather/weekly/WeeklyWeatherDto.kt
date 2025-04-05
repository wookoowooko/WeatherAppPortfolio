package io.wookoo.network.dto.weather.weekly

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyWeatherDto(
    @SerialName("time") val time: List<Long>,
    @SerialName("weather_code") val weatherCode: List<Int>,
    @SerialName("temperature_2m_max") val tempMax: List<Double>,
    @SerialName("temperature_2m_min") val tempMin: List<Double>,
    @SerialName("apparent_temperature_max") val apparentTempMax: List<Double>,
    @SerialName("apparent_temperature_min") val apparentTempMin: List<Double>,
    @SerialName("sunrise") val sunrise: List<Long>,
    @SerialName("sunset") val sunset: List<Long>,
    @SerialName("daylight_duration") val dayLightDuration: List<Double>,
    @SerialName("sunshine_duration") val sunshineDuration: List<Double>,
    @SerialName("uv_index_max") val uvIndexMax: List<Double>,
    @SerialName("precipitation_sum") val precipitationSum: List<Double>,
    @SerialName("rain_sum") val rainSum: List<Double>,
    @SerialName("showers_sum") val showersSum: List<Double>,
    @SerialName("snowfall_sum") val snowfallSum: List<Double>,
    @SerialName("precipitation_probability_max") val precipitationProbabilityMax: List<Int>,
    @SerialName("wind_speed_10m_max") val windSpeedMax: List<Double>,
    @SerialName("wind_gusts_10m_max") val windGustsMax: List<Double>,
    @SerialName("wind_direction_10m_dominant") val windDirectionMax: List<Int>,
)
