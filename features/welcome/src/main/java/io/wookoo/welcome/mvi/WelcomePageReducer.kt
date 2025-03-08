package io.wookoo.welcome.mvi

import io.wookoo.common.mvi.Reducer
import javax.inject.Inject

class WelcomePageReducer @Inject constructor() :
    Reducer<WelcomePageState, WelcomePageIntent> {

    override fun reduce(
        state: WelcomePageState,
        intent: WelcomePageIntent,
    ): WelcomePageState {
        return when (intent) {
            is OnSearchQueryChange ->
                state.copy(searchQuery = intent.query)

            is OnSearchedGeoItemClick ->
                state.copy(
                    isSearchExpanded = false,
                    city = intent.geoItem.cityName,
                    country = intent.geoItem.country,
                    latitude = intent.geoItem.latitude,
                    longitude = intent.geoItem.longitude
                )

            is OnAppBarExpandChange ->
                state.copy(isSearchExpanded = intent.state)

            is OnSearchGeoLocationClick ->
                state.copy(isGeolocationSearchInProgress = true)

            is UpdateGeolocationFromGpsSensors -> {
                println("Reducer: lat=${intent.lat}, lon=${intent.long}") // <--- Логируем
                state.copy(
                    latitude = intent.lat,
                    longitude = intent.long,
                    isGeolocationSearchInProgress = false
                )
            }

            is OnLoading ->
                state.copy(isLoading = true)

            is OnSuccessSearchLocation ->
                state.copy(results = intent.results)

            is OnErrorSearchLocation ->
                state.copy(results = emptyList())

            is OnErrorFetchReversGeocoding -> {
                state.copy(city = "")
            }

            is OnSuccessFetchReversGeocoding -> {
                state.copy(city = intent.city, country = intent.country)
            }

            is OnQueryIsEmpty ->
                state.copy(isLoading = false, results = emptyList())

            is OnLoadingFinish ->
                state.copy(isLoading = false)

            OnContinueButtonClick -> state
            OnRequestGeoLocationPermission -> state

            is AnySuccess, is AnyFailure ->
                state.copy(isLoading = false)
        }
    }
}
