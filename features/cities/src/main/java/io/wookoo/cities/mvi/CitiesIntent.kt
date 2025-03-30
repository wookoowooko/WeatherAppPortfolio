package io.wookoo.cities.mvi

import io.wookoo.models.geocoding.GeocodingDomainUI
import io.wookoo.models.weather.current.CurrentWeatherDomain

sealed interface CitiesIntent
interface Completable : CitiesIntent

data object OnLoading : CitiesIntent
data object OnSearchInProgress : CitiesIntent
data class OnSearchQueryChange(val query: String) : CitiesIntent
data class OnCitiesLoaded(val cities: List<io.wookoo.models.weather.current.CurrentWeatherDomain>) : CitiesIntent
data class OnSearchedGeoItemCardClick(val geoItem: io.wookoo.models.geocoding.GeocodingDomainUI) : CitiesIntent
data class OnChangeBottomSheetVisibility(val expandValue: Boolean) : CitiesIntent
data class OnDeleteCity(val geoItemId: Long) : CitiesIntent
data object OnSearchInProgressDone : Completable

data object OnLoadingFinish : Completable
data object OnErrorUpdateGeolocationFromGpsSensors : Completable
data object OnErrorFetchReversGeocodingFromApi : Completable

data class OnSuccessFetchReversGeocodingFromApi(
    val gpsItem: io.wookoo.models.geocoding.GeocodingDomainUI
) : Completable

data object OnQueryIsEmpty : Completable
data object OnErrorSearchLocation : Completable

data class OnSuccessSearchLocation(val results: List<io.wookoo.models.geocoding.GeocodingDomainUI>) : Completable

data object OnRequestGeoLocationPermission : CitiesIntent
data object OnGPSClick : CitiesIntent
data class OnUpdateNetworkState(val isOffline: Boolean) : CitiesIntent
data class OnUpdateCurrentGeo(val geoItemId: Long) : Completable
