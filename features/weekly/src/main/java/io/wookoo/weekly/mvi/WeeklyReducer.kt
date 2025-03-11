package io.wookoo.weekly.mvi

import io.wookoo.common.mvi.Reducer
import io.wookoo.domain.usecases.ConvertUnixDateToDayNameDayNumberUseCase
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.weekly.uimappers.asUiCalendarList
import javax.inject.Inject

class WeeklyReducer @Inject constructor(
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    private val convertUnixDateToDayNameDayNumberUseCase: ConvertUnixDateToDayNameDayNumberUseCase,
) : Reducer<WeeklyState, WeeklyIntent> {
    override fun reduce(
        state: WeeklyState,
        intent: WeeklyIntent,
    ): WeeklyState {
        return when (intent) {
            is OnCalendarItemClick -> {
                state.copy(
                    selectedCalendarItemIndex = intent.indexPosition,
                    weeklyCalendar = state.weeklyCalendar.mapIndexed { index, day ->
                        if (index == intent.indexPosition) {
                            day.copy(isSelected = true)
                        } else {
                            day.copy(isSelected = false)
                        }
                    }
                )
            }

            is OnObserveSelectedDayPositionChanged -> {
                val weekResponse = state.weatherResponse ?: return state
                state.copy(
                    mainWeatherRecyclerItems = state.mainWeatherRecyclerItems.mapFromResponse(
                        weekResponse = weekResponse,
                        selectedIndex = intent.selectedIndex,
                        convertWeatherCodeToEnumUseCase = convertWeatherCodeToEnumUseCase,
                        formatWindDirectionUseCase = formatWindDirectionUseCase,
                        convertUnixTimeUseCase = convertUnixTimeUseCase
                    )
                )
            }

            is OnObserveWeeklyForecast -> {
                state.copy(
                    weatherResponse = intent.forecast,
                    weeklyCalendar = intent.forecast.asUiCalendarList(
                        convertUnixDateToDayNameDayNumberUseCase = convertUnixDateToDayNameDayNumberUseCase,
                        convertWeatherCodeToEnumUseCase = convertWeatherCodeToEnumUseCase
                    ).mapIndexed { index, day ->
                        if (index == state.selectedCalendarItemIndex) {
                            day.copy(isSelected = true)
                        } else {
                            day.copy(isSelected = false)
                        }
                    }
                )
            }

            OnLoading -> state.copy(isLoading = true)
            OnLoadingFinish -> state.copy(isLoading = false)
        }
    }
}
