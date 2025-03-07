package io.wookoo.main.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.usecases.ConvertDateUseCase
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.HourlyModelToHourlyListUseCase
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.domain.utils.onError
import io.wookoo.domain.utils.onSuccess
import io.wookoo.geolocation.WeatherLocationManager
import io.wookoo.main.uimappers.asUICurrentWeather
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
    private val hourlyModelToHourlyListUseCase: HourlyModelToHourlyListUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    private val dataStore: IDataStoreRepo,
    private val weatherLocationManager: WeatherLocationManager,
) : ViewModel() {

    private var searchJob: Job? = null

    //    private var geonamesJob: Job? = null
    private val _state = MutableStateFlow(MainPageContract.MainPageState())

    val state = _state.onStart {
        observeSearchQuery()
//        observeLocationChanges()
        observeUserSettings()
        observeUserGeolocationChanges()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainPageContract.MainPageState()
    )

    private fun observeUserSettings() {
        viewModelScope.launch {
            dataStore.userSettings.collect { settings ->
                _state.update {
                    it.copy(userSettings = settings)
                }
            }
        }
    }

    fun onIntent(intent: MainPageContract.OnIntent) {
        when (intent) {
            is MainPageContract.OnIntent.OnSearchQueryChange -> changeSearchQuery(intent)
            is MainPageContract.OnIntent.OnExpandSearchBar -> onChangeExpandSearchBar(intent)
            is MainPageContract.OnIntent.OnSearchedGeoItemClick -> {
                viewModelScope.launch { onSelectResultCardClick(intent) }
            }

            is MainPageContract.OnIntent.OnGeolocationIconClick -> getGeolocation()
            else -> Unit
        }
    }

    private suspend fun onSelectResultCardClick(intent: MainPageContract.OnIntent.OnSearchedGeoItemClick) {
        dataStore.saveUserLocation(
            latitude = intent.geoItem.latitude,
            longitude = intent.geoItem.longitude
        )

        _state.update {
            it.copy(
                searchExpanded = false,
                city = intent.geoItem.cityName,
                country = intent.geoItem.country,
            )
        }
    }

    private fun getGeolocation() {
        _state.update { it.copy(isGeolocationSearchInProgress = true) }
        weatherLocationManager.getCurrentLocation { lat, lon ->
            viewModelScope.launch {
                dataStore.saveUserLocation(
                    latitude = lat,
                    longitude = lon
                )
            }
            _state.update {
                it.copy(isGeolocationSearchInProgress = false)
            }
        }
    }

    private fun onChangeExpandSearchBar(intent: MainPageContract.OnIntent.OnExpandSearchBar) {
        _state.update { it.copy(searchExpanded = intent.expandValue) }
    }

    private fun changeSearchQuery(intent: MainPageContract.OnIntent.OnSearchQueryChange) {
        _state.update { it.copy(searchQuery = intent.query) }
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
                        _state.update { it.copy(searchResults = emptyList()) }
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

    //    private fun observeLocationChanges() {
//        state.map { it.userSettings }
//            .distinctUntilChanged()
//            .onEach { }
//            .launchIn(viewModelScope)
//    }
    private fun observeUserGeolocationChanges() {
        state.map { it.userSettings.location.latitude }
            .filter { it != 0.0 }
            .distinctUntilChanged()
            .onEach {
                searchReverseGeoLocation()
                getCurrentWeather()
            }
            .launchIn(viewModelScope)
    }

    private fun searchReverseGeoLocation() = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }
        masterRepository.getReverseGeocodingLocation(
            latitude = state.value.userSettings.location.latitude,
            longitude = state.value.userSettings.location.longitude,
            language = "ru"
        ).onSuccess { searchResults ->
            _state.update {
                it.copy(
                    isLoading = false,
                    city = searchResults.geonames.first().name
                )
            }
        }.onError { error ->
            _state.update {
                it.copy(
                    city = "",
                    isLoading = false,
                )
            }
        }
    }

    private fun getCurrentWeather() {
        viewModelScope.launch {
            masterRepository.getCurrentWeather(
                latitude = state.value.userSettings.location.latitude,
                longitude = state.value.userSettings.location.longitude
            )
                .onError {
                    Log.d(TAG, "error: $it")
                }
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            currentWeather = response.asUICurrentWeather(
                                hourlyModelToHourlyListUseCase = hourlyModelToHourlyListUseCase,
                                convertDateUseCase = convertDateUseCase,
                                convertWeatherCodeToEnumUseCase = convertWeatherCodeToEnumUseCase,
                                convertUnixTimeUseCase = convertUnixTimeUseCase,
                                formatWindDirectionUseCase = formatWindDirectionUseCase
                            )
                        )
                    }
                }
        }
    }

    companion object {
        private const val TAG = "MainPageViewModel"
    }

    override fun onCleared() {
        searchJob?.cancel()
//        geonamesJob?.cancel()
    }
}
