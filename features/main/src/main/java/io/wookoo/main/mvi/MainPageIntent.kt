package io.wookoo.main.mvi

import io.wookoo.domain.model.geocoding.GeocodingDomainUI
import io.wookoo.domain.model.weather.current.CurrentWeatherDomain
import io.wookoo.domain.utils.AppError

sealed interface MainPageIntent

interface Completable : MainPageIntent

// Unit And Error Completable
data object OnLoadingFinish : Completable
data object OnQueryIsEmpty : Completable
data object OnErrorSearchLocation : Completable
data object OnErrorUpdateGeolocationFromGpsSensors : Completable
data object OnErrorFetchReversGeocodingFromApi : Completable

// Success Completable
data class OnSuccessFetchReversGeocodingFromApi(val city: String, val country: String) : Completable
data object OnSuccessfullyUpdateGeolocationFromGpsSensors : Completable
data class OnSuccessSearchLocation(val results: List<GeocodingDomainUI>) : Completable
data class OnSuccessFetchCurrentWeatherFromApi(val cachedResult: CurrentWeatherDomain) :
    Completable

// object MainPageIntent
data object OnLoading : MainPageIntent
data object OnGeolocationIconClick : MainPageIntent
data object OnRequestGeoLocationPermission : MainPageIntent
data class OnNavigateToWeekly(val geoItemId: Long) :
    MainPageIntent
data object OnNavigateToCities : MainPageIntent

// data MainPageIntent
data class OnSearchQueryChange(val query: String) : MainPageIntent
data class OnExpandSearchBar(val expandValue: Boolean) : MainPageIntent
data class OnSearchedGeoItemCardClick(val geoItem: GeocodingDomainUI) : MainPageIntent
data class UpdateCityListCount(val count: Int) : MainPageIntent
data class SetPagerPosition(val position: Int) : MainPageIntent

sealed interface MainPageEffect {
    data class OnShowSnackBar(val message: AppError) : MainPageEffect
    data object OnShowSettingsDialog : MainPageEffect
}
