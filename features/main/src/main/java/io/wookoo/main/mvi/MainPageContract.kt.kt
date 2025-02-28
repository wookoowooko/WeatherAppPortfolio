package io.wookoo.main.mvi

import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.enums.WindDirection
import io.wookoo.domain.model.geocoding.GeocodingSearchModel
import io.wookoo.domain.model.weather.current.HourlyModelItem

class MainPageContract {

    data class MainPageState(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
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
    )

    sealed interface OnIntent {
        data class OnSearchQueryChange(val query: String) : OnIntent
        data class OnExpandSearchBar(val expandValue: Boolean) : OnIntent
        data class OnGeoLocationClick(val geoItem: GeocodingSearchModel) : OnIntent
    }
}
