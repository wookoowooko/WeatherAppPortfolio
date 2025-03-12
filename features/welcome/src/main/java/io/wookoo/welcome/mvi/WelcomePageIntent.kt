package io.wookoo.welcome.mvi

import io.wookoo.domain.model.geocoding.GeocodingSearchModel
import io.wookoo.domain.utils.AppError

sealed interface WelcomePageIntent

interface Completable : WelcomePageIntent

// Unit And Error Completable
data object OnLoadingFinish : Completable
data object OnQueryIsEmpty : Completable
data object OnErrorUpdateGeolocationFromGpsSensors : Completable
data object OnErrorFetchReversGeocodingFromApi : Completable
data object OnErrorSearchLocation : Completable

// Success Completable
data class OnSuccessFetchReversGeocodingFromApi(val city: String, val country: String) : Completable
data class OnSuccessfullyUpdateGeolocationFromGpsSensors(val lat: Double, val long: Double) : Completable
data class OnSuccessSearchLocation(val results: List<GeocodingSearchModel>) : Completable

// object WelcomePageIntent
data object OnLoading : WelcomePageIntent
data object OnContinueButtonClick : WelcomePageIntent
data object OnSearchGeoLocationClick : WelcomePageIntent
data object OnRequestGeoLocationPermission : WelcomePageIntent

// data WelcomePageIntent
data class OnSearchQueryChange(val query: String) : WelcomePageIntent
data class OnSearchedGeoItemClick(val geoItem: GeocodingSearchModel) : WelcomePageIntent
data class OnAppBarExpandChange(val state: Boolean) : WelcomePageIntent
data class OnUpdateNetworkState(val isOffline: Boolean) : WelcomePageIntent

