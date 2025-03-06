package io.wookoo.weekly.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.usecases.ConvertUnixDateToDayNameDayNumberUseCase
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.domain.utils.onError
import io.wookoo.domain.utils.onSuccess
import io.wookoo.weekly.uimappers.asUiCalendarList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeeklyViewModel @Inject constructor(
    private val masterRepository: IMasterWeatherRepo,
    dataStore: IDataStoreRepo,
    private val convertUnixDateToDayNameDayNumberUseCase: ConvertUnixDateToDayNameDayNumberUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(WeeklyViewModelContract.WeeklyState())
    private val settings = dataStore.userSettings

    val state = _state.onStart {
        getWeeklyWeather()
        observeSelectedDayPositionChanged()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        WeeklyViewModelContract.WeeklyState()
    )


    fun onIntent(intent: WeeklyViewModelContract.OnIntent) {
        Log.d(TAG, "onIntent received: $intent")
        when (intent) {
            is WeeklyViewModelContract.OnIntent.OnCalendarItemClick -> {
                Log.d(TAG, "OnCalendarItemClick: ${intent.indexPosition}")
                onCalendarItemClick(intent)
            }
        }
    }

    private fun onCalendarItemClick(intent: WeeklyViewModelContract.OnIntent.OnCalendarItemClick) {
        _state.update {
            it.copy(
                selectedCalendarItemIndex = intent.indexPosition,
                weeklyCalendar = it.weeklyCalendar.mapIndexed { index, day ->
                    if (index == intent.indexPosition) {
                        day.copy(isSelected = true)
                    } else {
                        day.copy(isSelected = false)
                    }
                }
            )
        }
    }

    private fun getWeeklyWeather() {
        viewModelScope.launch {
            val location = settings.first().location
            if (location.latitude == 0.0 || location.longitude == 0.0) return@launch

            masterRepository.getWeeklyWeather(location.latitude, location.longitude)
                .onError { error ->
                    Log.d(TAG, "Error fetching weather: $error")
                }
                .onSuccess { forecast: WeeklyWeatherResponseModel ->
                    _state.update { currentState ->
                        currentState.copy(
                            weatherResponse = forecast,
                            weeklyCalendar = forecast.asUiCalendarList(
                                convertUnixDateToDayNameDayNumberUseCase = convertUnixDateToDayNameDayNumberUseCase,
                                convertWeatherCodeToEnumUseCase = convertWeatherCodeToEnumUseCase
                            ).mapIndexed { index, day ->
                                if (index == state.value.selectedCalendarItemIndex) {
                                    day.copy(isSelected = true)
                                } else {
                                    day.copy(isSelected = false)
                                }
                            }
                        )
                    }
                }

        }
    }

    private fun observeSelectedDayPositionChanged() {
        state.map { it.selectedCalendarItemIndex }
            .onEach { selectedIndex ->
                _state.update { currentState ->
                    val weekResponse = currentState.weatherResponse ?: return@onEach
                    currentState.copy(
                        mainWeatherRecyclerItems = currentState.mainWeatherRecyclerItems.mapFromResponse(
                            weekResponse,
                            selectedIndex,
                            convertWeatherCodeToEnumUseCase,
                            formatWindDirectionUseCase,
                            convertUnixTimeUseCase
                        )
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    companion object {
        private const val TAG = "WeeklyViewModel"
    }
}
