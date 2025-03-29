package io.wookoo.main.mvi

import io.wookoo.main.uimodels.UiCurrentWeatherModel

data class MainPageState(
    val isNow: Boolean = false,
    val isLoading: Boolean = true,
    val city: String = "",
    val country: String = "",
    val currentWeather: UiCurrentWeatherModel = UiCurrentWeatherModel(),
    val cityListCount: Int = 0,
    val pagerPosition: Int = 0,
)
