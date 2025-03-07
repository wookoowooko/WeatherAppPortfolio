package io.wookoo.welcome.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.onError
import io.wookoo.domain.utils.onSuccess
import io.wookoo.geolocation.WeatherLocationManager
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
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
class WelcomePageViewModel @Inject constructor(
    private val masterRepository: IMasterWeatherRepo,
    private val dataStore: IDataStoreRepo,
    private val weatherLocationManager: WeatherLocationManager,
) : ViewModel() {

    private var searchJob: Job? = null
    private var geonamesJob: Job? = null

    private val _state = MutableStateFlow(WelcomePageContract.WelcomePageState())

    val state = _state.onStart {
        observeSearchQuery()
        observeLocationChanges()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        WelcomePageContract.WelcomePageState()
    )

    fun onIntent(intent: WelcomePageContract.OnIntent) {
        when (intent) {
            is WelcomePageContract.OnIntent.OnSearchQueryChange -> changeSearchQuery(intent)
            is WelcomePageContract.OnIntent.OnSearchedGeoItemClick -> onSelectResultCard(intent)
            is WelcomePageContract.OnIntent.OnExpandedChange -> onChangeExpandSearchBar(intent)
            is WelcomePageContract.OnIntent.OnContinueButtonClick -> viewModelScope.launch { continueButtonClick() }
            is WelcomePageContract.OnIntent.OnSearchGeoLocationClick -> getGeolocation()
            else -> Unit
        }
    }

    private fun changeSearchQuery(intent: WelcomePageContract.OnIntent.OnSearchQueryChange) {
        _state.update {
            it.copy(searchQuery = intent.query)
        }
    }

    private fun onSelectResultCard(intent: WelcomePageContract.OnIntent.OnSearchedGeoItemClick) {
        _state.update {
            it.copy(
                isSearchExpanded = false,
                city = intent.geoItem.cityName,
                country = intent.geoItem.country,
                latitude = intent.geoItem.latitude,
                longitude = intent.geoItem.longitude
            )
        }
    }

    private fun onChangeExpandSearchBar(intent: WelcomePageContract.OnIntent.OnExpandedChange) {
        _state.update {
            it.copy(isSearchExpanded = intent.state)
        }
    }

    private fun getGeolocation() {
        _state.update { it.copy(isGeolocationSearchInProgress = true) }
        weatherLocationManager.getCurrentLocation { lat, lon ->
            _state.update {
                it.copy(
                    latitude = lat,
                    longitude = lon,
                    isGeolocationSearchInProgress = false
                )
            }
        }
    }

    private suspend fun continueButtonClick() {
        dataStore.saveUserLocation(
            latitude = state.value.latitude,
            longitude = state.value.longitude
        )
        dataStore.saveInitialLocationPicked(true)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(THRESHOLD)
            .onEach { query ->
                when {
                    query.isBlank() -> { _state.update { it.copy(results = emptyList()) } }
                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchLocation(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchLocation(query: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        masterRepository.getSearchedLocation(query, language = "ru")
            .onSuccess { searchResults ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        results = searchResults.results
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        results = emptyList(),
                        isLoading = false,
                    )
                }
            }
    }

    private fun observeLocationChanges() {
        state.map {
            it.latitude
        }.distinctUntilChanged()
            .filterNotNull()
            .filter { it != 0.0 }
            .onEach {
                geonamesJob?.cancel()
                geonamesJob = fetchReversGeocoding()
            }
            .launchIn(viewModelScope)
    }

    private fun fetchReversGeocoding() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        masterRepository.getReverseGeocodingLocation(
            latitude = state.value.latitude,
            longitude = state.value.longitude,
            language = "ru"
        ).onSuccess { searchResults ->
            _state.update {
                it.copy(
                    isLoading = false,
                    city = searchResults.geonames.first().name,
                    country = searchResults.geonames.first().countryName
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

    companion object {
        private const val THRESHOLD = 500L
    }

    override fun onCleared() {
        searchJob?.cancel()
        geonamesJob?.cancel()
    }
}
