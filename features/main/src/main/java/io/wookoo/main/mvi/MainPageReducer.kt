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

            is Completable -> state.copy(isLoading = false)
                .let {
                    when (intent) {
                        is OnGetCurrentForecast -> {
                            it.copy(
                                city = intent.cachedResult.geo.cityName,
                                country = intent.cachedResult.geo.countryName,
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
