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
            is OnLoading -> state.copy(isLoading = true)
            is OnSearchQueryChange -> state.copy(searchQuery = intent.query)
            is OnSearchedGeoItemClick ->
                state.copy(
                    isSearchExpanded = false,
                    city = intent.geoItem.cityName,
                    country = intent.geoItem.country,
                    latitude = intent.geoItem.latitude,
                    longitude = intent.geoItem.longitude
                )

            is OnAppBarExpandChange -> state.copy(isSearchExpanded = intent.state)
            is OnSearchGeoLocationClick -> state.copy(isGeolocationSearchInProgress = true)

            is Completable ->
                state.copy(isLoading = false, isGeolocationSearchInProgress = false).let {
                    when (intent) {
                        is OnSuccessSearchLocation -> it.copy(results = intent.results)
                        is OnErrorSearchLocation -> it.copy(results = emptyList())
                        is OnErrorFetchReversGeocodingFromApi -> it.copy(city = "", country = "")
                        is OnSuccessFetchReversGeocodingFromApi -> it.copy(
                            city = intent.city,
                            country = intent.country,
                            geoItemId = intent.geoItemId
                        )

                        is OnSuccessfullyUpdateGeolocationFromGpsSensors -> {
                            state.copy(
                                latitude = intent.lat,
                                longitude = intent.long,
                            )
                        }

                        is OnQueryIsEmpty -> it.copy(results = emptyList())
                        else -> it
                    }
                }

            else -> state
        }
    }
}
