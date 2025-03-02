package io.wookoo.welcome.mvi

import io.wookoo.domain.model.geocoding.GeocodingSearchModel

class WelcomePageContract {
    data class WelcomePageState(
        val city: String = "",
        val country: String = "",
        val isSearchExpanded: Boolean = false,
        val isLoading: Boolean = false,
        val searchQuery: String = "",
        val results: List<GeocodingSearchModel> = emptyList(),
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val isGeolocationSearchInProgress: Boolean = false,
    )

    sealed interface OnIntent {
        data class OnSearchQueryChange(val query: String) : OnIntent
        data class OnSearchedGeoItemClick(val geoItem: GeocodingSearchModel) : OnIntent
        data class OnExpandedChange(val state: Boolean) : OnIntent
        data object OnContinueButtonClick : OnIntent
        data object OnSearchGeoLocationClick : OnIntent
        data object OnRequestGeoLocationPermission : OnIntent
    }
}
