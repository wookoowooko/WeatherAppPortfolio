package io.wookoo.main.mvi

import io.wookoo.domain.usecases.UiCurrentWeatherModel

data class MainPageState(
    val isNow: Boolean = false,
    val isLoading: Boolean = true,
    val currentWeather: UiCurrentWeatherModel = UiCurrentWeatherModel(),
    val cityListCount: Int = 0,
    val pagerPosition: Int = 0,
)
