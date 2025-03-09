package io.wookoo.main.mvi

import io.wookoo.domain.model.geocoding.GeocodingSearchModel

sealed interface MainPageIntent
data class OnSearchQueryChange(val query: String) : MainPageIntent
data class OnExpandSearchBar(val expandValue: Boolean) : MainPageIntent
data class OnSearchedGeoItemClick(val geoItem: GeocodingSearchModel) : MainPageIntent
data object OnGeolocationIconClick : MainPageIntent
data object OnRequestGeoLocationPermission : MainPageIntent
data object OnNavigateToWeekly : MainPageIntent
