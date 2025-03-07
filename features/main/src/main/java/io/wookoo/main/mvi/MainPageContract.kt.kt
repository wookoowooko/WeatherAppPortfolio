package io.wookoo.main.mvi

import io.wookoo.domain.model.geocoding.GeocodingSearchModel
import io.wookoo.domain.settings.UserSettingsModel
import io.wookoo.main.uimodels.UiCurrentWeatherModel

class MainPageContract {

    data class MainPageState(
        val searchExpanded: Boolean = false,
        val searchQuery: String = "",
        val searchResults: List<GeocodingSearchModel> = emptyList(),
        val isNow: Boolean = false,
        val isLoading: Boolean = true,
        val city: String = "",
        val country: String = "",
        val userSettings: UserSettingsModel = UserSettingsModel(),
        val isGeolocationSearchInProgress: Boolean = false,
        val currentWeather: UiCurrentWeatherModel = UiCurrentWeatherModel(),
    )

    sealed interface OnIntent {
        data class OnSearchQueryChange(val query: String) : OnIntent
        data class OnExpandSearchBar(val expandValue: Boolean) : OnIntent
        data class OnSearchedGeoItemClick(val geoItem: GeocodingSearchModel) : OnIntent
        data object OnGeolocationIconClick : OnIntent
        data object OnRequestGeoLocationPermission : OnIntent
        data object OnNavigateToWeekly : OnIntent
    }
}
