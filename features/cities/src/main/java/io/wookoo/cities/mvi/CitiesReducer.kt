package io.wookoo.cities.mvi

import io.wookoo.cities.uimappers.asUiCity
import io.wookoo.common.mvi.Reducer
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.usecases.ConvertDateUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import javax.inject.Inject

class CitiesReducer @Inject constructor(
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val convertDateUseCase: ConvertDateUseCase,

    ) : Reducer<CitiesState, CitiesIntent> {
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
                state.copy(
                    cities = intent.cities.map { currentWeather: CurrentWeatherResponseModel ->
                        currentWeather.asUiCity(
                            convertWeatherCodeToEnumUseCase = convertWeatherCodeToEnumUseCase,
                            convertDateUseCase = convertDateUseCase
                        )
                    }
                )
            }

            is OnSearchedGeoItemCardClick ->
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
