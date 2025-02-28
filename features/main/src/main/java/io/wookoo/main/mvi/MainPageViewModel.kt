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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("LargeClass")
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

    private var searchJob: Job? = null
    private val _state = MutableStateFlow(MainPageContract.MainPageState())

    val state = _state.onStart {
        observeSearchQuery()
        observeLocationChanges()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainPageContract.MainPageState()
    )

    fun onIntent(intent: MainPageContract.OnIntent) {
        when (intent) {
            is MainPageContract.OnIntent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = intent.query) }
            }

            is MainPageContract.OnIntent.OnExpandSearchBar -> {
                _state.update { it.copy(searchExpanded = intent.expandValue) }
            }

            is MainPageContract.OnIntent.OnGeoLocationClick -> {
                _state.update {
                    it.copy(
                        searchExpanded = false,
                        city = intent.geoItem.name,
                        country = intent.geoItem.country,
                        latitude = intent.geoItem.latitude,
                        longitude = intent.geoItem.longitude
                    )
                }
            }
        }
    }

    private fun observeLocationChanges() {
        state.map { it.city }
            .distinctUntilChanged()
            .onEach { getCurrentWeather() }
            .launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                searchResults = emptyList()
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchLocation(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchLocation(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }
        masterRepository.getSearchedLocation(query, language = "ru")
            .onSuccess { searchResults ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResults = searchResults.results
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                    )
                }
            }
    }

    private fun getCurrentWeather() {
        viewModelScope.launch {
            masterRepository.getCurrentWeather(
                latitude = state.value.latitude,
                longitude = state.value.longitude
            )
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
