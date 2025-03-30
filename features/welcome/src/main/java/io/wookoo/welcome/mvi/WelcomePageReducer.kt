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
                    geoItem = intent.geoItem,
                    isSearchExpanded = false,
                    searchQuery = "",
                    results = emptyList()
                )

            is OnUpdateNetworkState -> state.copy(isOffline = intent.isOffline)
            is OnAppBarExpandChange -> if (state.isOffline) {
                state
            } else {
                state.copy(isSearchExpanded = intent.state)
            }

            is OnSearchGeoLocationClick -> state.copy(
                isGeolocationSearchInProgress = true
            )

            is OnErrorSearchLocation -> state.copy(results = emptyList())

            is OnSuccessSearchLocation -> state.copy(results = intent.results)

            is UpdateSelectedTemperature -> {
                val dataUnit =
                    state.temperatureUnitOptions.find { it.apiValue == intent.temperatureUnit }
                state.copy(selectedTemperatureUnit = dataUnit ?: state.selectedTemperatureUnit)
            }

            is UpdateSelectedWindSpeed -> {
                val dataUnit =
                    state.windSpeedUnitOptions.find { it.apiValue == intent.windSpeedUnit }
                state.copy(selectedWindSpeedUnit = dataUnit ?: state.selectedWindSpeedUnit)
            }

            is UpdateSelectedPrecipitation -> {
                val dataUnit =
                    state.precipitationUnitOptions.find { it.apiValue == intent.precipitationUnit }
                state.copy(selectedPrecipitationUnit = dataUnit ?: state.selectedPrecipitationUnit)
            }

            is Completable ->
                state.copy(isLoading = false, isGeolocationSearchInProgress = false).let {
                    when (intent) {
                        is OnSuccessFetchReversGeocodingFromApi -> it.copy(
                            geoItem = intent.gpsItem,
                        )

                        is OnQueryIsEmptyClearResults -> it.copy(results = emptyList())
                        else -> it
                    }
                }

            else -> state
        }
    }
}
