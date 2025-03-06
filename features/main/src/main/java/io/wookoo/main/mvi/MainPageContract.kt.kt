package io.wookoo.main.mvi

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.units.WindDirection
import io.wookoo.domain.model.geocoding.GeocodingSearchModel
import io.wookoo.domain.model.weather.current.HourlyModelItem
import io.wookoo.domain.settings.UserSettingsModel

class MainPageContract {

    data class MainPageState(
        val searchExpanded: Boolean = false,
        val searchQuery: String = "",
        val searchResults: List<GeocodingSearchModel> = emptyList(),
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
        val userSettings: UserSettingsModel = UserSettingsModel(),
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val isGeolocationSearchInProgress: Boolean = false,
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
