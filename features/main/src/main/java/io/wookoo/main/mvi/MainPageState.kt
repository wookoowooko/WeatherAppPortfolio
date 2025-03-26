package io.wookoo.main.mvi

import io.wookoo.domain.model.geocoding.GeocodingDomainUI
import io.wookoo.domain.settings.UserSettingsModel
import io.wookoo.main.uimodels.UiCurrentWeatherModel

data class MainPageState(
    val searchExpanded: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<GeocodingDomainUI> = emptyList(),
    val isNow: Boolean = false,
    val isLoading: Boolean = true,
    val city: String = "",
    val country: String = "",
//    val userSettings: UserSettingsModel = UserSettingsModel(),
    val isGeolocationSearchInProgress: Boolean = false,
    val currentWeather: UiCurrentWeatherModel = UiCurrentWeatherModel(),
    val cityListCount: Int = 0,
    val pagerPosition: Int = 0,
)
