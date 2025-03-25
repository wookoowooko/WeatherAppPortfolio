package io.wookoo.welcome.mvi

import io.wookoo.domain.model.geocoding.GeocodingDomainUI

sealed interface WelcomePageIntent

interface Completable : WelcomePageIntent

// Unit And Error Completable
data object OnLoadingFinish : Completable
data object OnQueryIsEmptyClearResults : Completable
data object OnErrorUpdateGeolocationFromGpsSensors : Completable
data object OnErrorFetchReversGeocodingFromApi : Completable
data object OnErrorSearchLocation : Completable

data class OnSuccessFetchReversGeocodingFromApi(
    val gpsItem: GeocodingDomainUI
) : Completable

data class OnSuccessSearchLocation(val results: List<GeocodingDomainUI>) : Completable

// object WelcomePageIntent
data object OnLoading : WelcomePageIntent
data object OnContinueButtonClick : WelcomePageIntent
data object OnSearchGeoLocationClick : WelcomePageIntent
data object OnRequestGeoLocationPermission : WelcomePageIntent

// data WelcomePageIntent
data class OnSearchQueryChange(val query: String) : WelcomePageIntent
data class OnSearchedGeoItemClick(val geoItem: GeocodingDomainUI) : WelcomePageIntent
data class OnAppBarExpandChange(val state: Boolean) : WelcomePageIntent
data class OnUpdateNetworkState(val isOffline: Boolean) : WelcomePageIntent
