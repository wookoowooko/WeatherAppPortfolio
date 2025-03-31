package io.wookoo.cities.mvi

import io.wookoo.common.mvi.Reducer
import javax.inject.Inject

class CitiesReducer @Inject constructor() : Reducer<CitiesState, CitiesIntent> {
    override fun reduce(
        state: CitiesState,
        intent: CitiesIntent,
    ): CitiesState {
        return when (intent) {
            is OnLoading -> state.copy(isLoading = true)
            is OnSearchInProgress -> state.copy(isProcessing = true)

            is OnSearchQueryChange -> state.copy(searchQuery = intent.query)
            is OnUpdateNetworkState -> state.copy(isOffline = intent.isOffline)

            is OnCitiesLoaded -> {
                state.copy(cities = intent.cities)
            }

            is OnSearchedGeoItemCardClick, is OnUpdateCurrentGeo ->
                state.copy(
                    bottomSheetExpanded = false,
                    searchQuery = "",
                    results = emptyList()
                )

            is OnGPSClick -> state.copy(
                isProcessing = true
            )

            is OnChangeBottomSheetVisibility -> state.copy(bottomSheetExpanded = intent.expandValue)

            is Completable ->
                state.copy(
                    isLoading = false,
                    isProcessing = false
                ).let {
                    when (intent) {
                        is OnSuccessFetchReversGeocodingFromApi -> it.copy(
                            gpsItem = intent.gpsItem,
                        )

                        is OnSuccessSearchLocation -> it.copy(results = intent.results)
                        is OnQueryIsEmpty -> it.copy(results = emptyList())
                        else -> it
                    }
                }

            else -> state
        }
    }
}
