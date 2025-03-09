package io.wookoo.welcome.mvi

import io.wookoo.domain.model.geocoding.GeocodingSearchModel
import io.wookoo.domain.utils.AppError

sealed interface WelcomePageIntent

interface Completable : WelcomePageIntent

data object OnLoading : WelcomePageIntent
data object OnLoadingFinish : Completable
data class UpdateGeolocationFromGpsSensors(val lat: Double, val long: Double) : WelcomePageIntent
data class OnSuccessSearchLocation(val results: List<GeocodingSearchModel>) : Completable
data object OnErrorSearchLocation : Completable
data class OnSearchQueryChange(val query: String) : WelcomePageIntent
data object OnQueryIsEmpty : WelcomePageIntent
data class OnSearchedGeoItemClick(val geoItem: GeocodingSearchModel) : WelcomePageIntent
data class OnAppBarExpandChange(val state: Boolean) : WelcomePageIntent
data object OnContinueButtonClick : WelcomePageIntent
data object OnSearchGeoLocationClick : WelcomePageIntent
data object OnRequestGeoLocationPermission : WelcomePageIntent

data class OnSuccessFetchReversGeocoding(val city: String, val country: String) : Completable
data object OnErrorFetchReversGeocoding : Completable

sealed interface SideEffect {
    data class ShowSnackBar(val message: AppError) : SideEffect
}
