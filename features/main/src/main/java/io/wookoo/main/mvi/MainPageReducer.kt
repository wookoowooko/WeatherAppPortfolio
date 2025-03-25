package io.wookoo.main.mvi

import io.wookoo.common.mvi.Reducer
import io.wookoo.domain.usecases.MapCurrentWeatherUiDataUseCase
import javax.inject.Inject

class MainPageReducer @Inject constructor(
    private val mapper: MapCurrentWeatherUiDataUseCase,
) : Reducer<MainPageState, MainPageIntent> {

    override fun reduce(state: MainPageState, intent: MainPageIntent): MainPageState {
        return when (intent) {
            is OnLoading -> state.copy(isLoading = true)
            is UpdateCityListCount -> state.copy(cityListCount = intent.count)
            is SetPagerPosition -> state.copy(pagerPosition = intent.position)

            is OnExpandSearchBar -> state.copy(searchExpanded = intent.expandValue)
            is OnSearchQueryChange -> state.copy(searchQuery = intent.query)
            is OnGeolocationIconClick -> state.copy(isGeolocationSearchInProgress = true)
            is OnSearchedGeoItemCardClick -> state.copy(searchExpanded = false)

            is Completable -> state.copy(isLoading = false, isGeolocationSearchInProgress = false)
                .let {
                    when (intent) {
                        is OnErrorFetchReversGeocodingFromApi -> it.copy(city = "", country = "")

                        is OnSuccessFetchReversGeocodingFromApi -> it.copy(
                            city = intent.city,
                            country = intent.country
                        )

                        is OnSuccessSearchLocation -> it.copy(
                            searchResults = intent.results
                        )

                        is OnSuccessFetchCurrentWeatherFromApi -> {
                            it.copy(
                                city = intent.cachedResult.geo.cityName,
                                country = intent.cachedResult.geo.countryName,
                                currentWeather = mapper(intent.cachedResult)
                            )
                        }

                        else -> it
                    }
                }

            else -> state
        }
    }
}
