package io.wookoo.cities.mvi

import io.wookoo.domain.model.geocoding.GeocodingSearchModel
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel

sealed interface CitiesIntent
interface Completable : CitiesIntent

data object OnLoading : CitiesIntent
data class OnSearchQueryChange(val query: String) : CitiesIntent
data class OnCitiesLoaded(val cities: List<CurrentWeatherResponseModel>) : CitiesIntent
data class OnSearchedGeoItemCardClick(val geoItem: GeocodingSearchModel) : CitiesIntent
data class OnChangeBottomSheetVisibility(val expandValue: Boolean) : CitiesIntent

// Unit And Error Completable
data object OnLoadingFinish : Completable
data object OnQueryIsEmpty : Completable
data object OnErrorSearchLocation : Completable


// Success Completable
data class OnSuccessSearchLocation(val results: List<GeocodingSearchModel>) : Completable

