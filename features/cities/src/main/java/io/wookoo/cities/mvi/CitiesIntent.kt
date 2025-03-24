package io.wookoo.cities.mvi

import io.wookoo.domain.model.geocoding.GeocodingModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel

sealed interface CitiesIntent
interface Completable : CitiesIntent

data object OnLoading : CitiesIntent
data object OnSearchInProgress : CitiesIntent
data class OnSearchQueryChange(val query: String) : CitiesIntent
data class OnCitiesLoaded(val cities: List<CurrentWeatherResponseModel>) : CitiesIntent
data class OnSearchedGeoItemCardClick(val geoItem: GeocodingModel) : CitiesIntent
data class OnChangeBottomSheetVisibility(val expandValue: Boolean) : CitiesIntent
data class OnDeleteCity(val geoItemId: Long) : CitiesIntent
data object OnSearchInProgressDone : Completable

data object OnLoadingFinish : Completable
data object OnErrorUpdateGeolocationFromGpsSensors : Completable
data object OnErrorFetchReversGeocodingFromApi : Completable

data class OnSuccessFetchReversGeocodingFromApi(
    val gpsItem: GeocodingModel
) : Completable

data object OnQueryIsEmpty : Completable
data object OnErrorSearchLocation : Completable

data class OnSuccessSearchLocation(val results: List<GeocodingModel>) : Completable

data object OnRequestGeoLocationPermission : CitiesIntent
data object OnGPSClick : CitiesIntent
data class OnUpdateNetworkState(val isOffline: Boolean) : CitiesIntent
data class OnUpdateCurrentGeo(val geoItemId: Long) : Completable
