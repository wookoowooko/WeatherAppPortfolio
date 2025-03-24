package io.wookoo.welcome.mvi

import io.wookoo.domain.model.geocoding.GeocodingModel

sealed interface WelcomePageIntent

interface Completable : WelcomePageIntent

// Unit And Error Completable
data object OnLoadingFinish : Completable
data object OnQueryIsEmptyClearResults : Completable
data object OnErrorUpdateGeolocationFromGpsSensors : Completable
data object OnErrorFetchReversGeocodingFromApi : Completable
data object OnErrorSearchLocation : Completable

// Success Completable
data class OnSuccessFetchReversGeocodingFromApi(
    val city: String,
    val country: String,
    val geoItemId: Long,
) : Completable

data class OnSuccessfullyUpdateGeolocationFromGpsSensors(val lat: Double, val long: Double) :
    WelcomePageIntent

data class OnSuccessSearchLocation(val results: List<GeocodingModel>) : Completable

// object WelcomePageIntent
data object OnLoading : WelcomePageIntent
data object OnContinueButtonClick : WelcomePageIntent
data object OnSearchGeoLocationClick : WelcomePageIntent
data object OnRequestGeoLocationPermission : WelcomePageIntent

// data WelcomePageIntent
data class OnSearchQueryChange(val query: String) : WelcomePageIntent
data class OnSearchedGeoItemClick(val geoItem: GeocodingModel) : WelcomePageIntent
data class OnAppBarExpandChange(val state: Boolean) : WelcomePageIntent
data class OnUpdateNetworkState(val isOffline: Boolean) : WelcomePageIntent
