package io.wookoo.cities.mvi

import io.wookoo.cities.uimappers.asUiCity
import io.wookoo.common.mvi.Reducer
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import javax.inject.Inject

class CitiesReducer @Inject constructor(
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,

    ) : Reducer<CitiesState, CitiesIntent> {
    override fun reduce(
        state: CitiesState,
        intent: CitiesIntent,
    ): CitiesState {
        return when (intent) {
            is OnLoading -> state.copy(isLoading = true)
            is OnSearchQueryChange -> state.copy(searchQuery = intent.query)
            is OnCitiesLoaded -> {
                state.copy(
                    cities =
                    intent.cities.map { currentWeather ->
                        currentWeather.asUiCity(
                            convertWeatherCodeToEnumUseCase = convertWeatherCodeToEnumUseCase
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

            is OnChangeBottomSheetVisibility -> state.copy(bottomSheetExpanded = intent.expandValue)

            is Completable ->
                state.copy(isLoading = false).let {
                    when (intent) {
                        is OnSuccessSearchLocation -> it.copy(results = intent.results)
                        is OnQueryIsEmpty -> it.copy(results = emptyList())
                        else -> it
                    }
                }

            else -> state
        }
    }
}
