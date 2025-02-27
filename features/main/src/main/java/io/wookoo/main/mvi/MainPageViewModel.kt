package io.wookoo.main.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wookoo.domain.enums.ApiUnit
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.usecases.ConvertDateUseCase
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.HourlyModelToHourlyListUseCase
import io.wookoo.domain.usecases.UnitFormatUseCase
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.domain.utils.onError
import io.wookoo.domain.utils.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val masterRepository: IMasterWeatherRepo,
    private val convertDateUseCase: ConvertDateUseCase,
    private val formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val unitFormatUseCase: UnitFormatUseCase,
    private val hourlyModelToHourlyListUseCase: HourlyModelToHourlyListUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainPageContract.MainPageState())

    val state = _state.onStart {
        getCurrentWeather()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainPageContract.MainPageState()
    )

    fun onIntent(intent: MainPageContract.OnIntent) {
        when (intent) {
            else -> {}
        }
    }

    private fun getCurrentWeather() {
        viewModelScope.launch {
            masterRepository.getCurrentWeather()
                .onError {
                    Log.d(TAG, "error: $it")
                }
                .onSuccess { model ->

                    _state.update {
                        it.copy(
                            hourlyList = hourlyModelToHourlyListUseCase(
                                hourlyModel = model.hourly,
                            ),
                            isLoading = false,
                            date = convertDateUseCase(model.current.time),
                            city = "",
                            country = "",
                            isDay = model.current.isDay,
                            humidity = unitFormatUseCase(
                                input = model.current.relativeHumidity,
                                unit = ApiUnit.PERCENT
                            ),
                            windSpeed = unitFormatUseCase(
                                input = model.current.wind.speed,
                                unit = ApiUnit.KMH
                            ),
                            windDirection = formatWindDirectionUseCase(model.current.wind.direction),
                            windGust = unitFormatUseCase(
                                input = model.current.wind.gust,
                                unit = ApiUnit.KMH
                            ),
                            precipitation = unitFormatUseCase(
                                input = model.current.precipitation.level,
                                unit = ApiUnit.MM
                            ),
                            temperature = unitFormatUseCase(
                                input = model.current.temperature,
                                unit = ApiUnit.CELSIUS
                            ),
                            temperatureFeelsLike = unitFormatUseCase(
                                input = model.current.feelsLike,
                                unit = ApiUnit.CELSIUS
                            ),
                            pressureMsl = unitFormatUseCase(
                                input = model.current.pressureMSL,
                                unit = ApiUnit.PRESSURE
                            ),
                            weatherStatus = convertWeatherCodeToEnumUseCase(model.current.weatherStatus),
                            sunriseTime = convertUnixTimeUseCase(model.daily.sunrise).first(),
                            sunsetTime = convertUnixTimeUseCase(model.daily.sunset).first(),
                            uvIndex = unitFormatUseCase(
                                model.daily.uvIndexMax.first(),
                                ApiUnit.UV_INDEX
                            )
                        )
                    }
                }
        }
    }

    companion object {
        private const val TAG = "MainPageViewModel"
    }
}
