package io.wookoo.main.mvi

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.enums.WindDirection
import io.wookoo.domain.model.HourlyModelItem

class MainPageContract {

    data class MainPageState(
        val isNow: Boolean = false,
        val isDay: Boolean = true,
        val isLoading: Boolean = true,
        val date: String = "",
        val city: String = "",
        val country: String = "",
        val humidity: String = "",
        val windSpeed: String = "",
        val windDirection: WindDirection = WindDirection.UNDETECTED,
        val windGust: String = "",
        val precipitation: String = "",
        val pressureMsl: String = "",
        val temperature: String = "",
        val temperatureFeelsLike: String = "",
        val weatherStatus: WeatherCondition = WeatherCondition.UNKNOWN,
        val hourlyList: List<HourlyModelItem> = emptyList(),
        val sunriseTime: String = "",
        val sunsetTime: String = "",
        val uvIndex: String = "",
    )

    sealed interface OnIntent
}
