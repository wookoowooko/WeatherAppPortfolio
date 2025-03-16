package io.wookoo.main.mvi

import io.wookoo.common.mvi.Reducer
import io.wookoo.domain.usecases.ConvertDateUseCase
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.HourlyModelToHourlyListUseCase
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.main.uimappers.asUICurrentWeather
import javax.inject.Inject

class MainPageReducer @Inject constructor(
    private val convertDateUseCase: ConvertDateUseCase,
    private val formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val hourlyModelToHourlyListUseCase: HourlyModelToHourlyListUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
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
                                city = intent.cachedResult.cityName,
                                country = intent.cachedResult.countryName,
                                currentWeather = intent.cachedResult.asUICurrentWeather(
                                    hourlyModelToHourlyListUseCase = hourlyModelToHourlyListUseCase,
                                    convertDateUseCase = convertDateUseCase,
                                    convertWeatherCodeToEnumUseCase = convertWeatherCodeToEnumUseCase,
                                    convertUnixTimeUseCase = convertUnixTimeUseCase,
                                    formatWindDirectionUseCase = formatWindDirectionUseCase
                                )
                            )
                        }

                        else -> it
                    }
                }

            else -> state
        }
    }
}
