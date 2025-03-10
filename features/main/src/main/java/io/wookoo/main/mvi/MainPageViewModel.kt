package io.wookoo.main.mvi

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val store: MainPageStore,
) : ViewModel() {
    val state = store.createState()
    val sideEffect = store.createSideEffect()
    fun onIntent(intent: MainPageIntent) = store.dispatch(intent)

    override fun onCleared() {
        println("cleared")
        store.clear()
    }
}
//
// @Suppress("LargeClass")
// @HiltViewModel
// class MainPageViewModel @Inject constructor(
//    private val masterRepository: IMasterWeatherRepo,
//    private val convertDateUseCase: ConvertDateUseCase,
//    private val formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
//    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
//    private val hourlyModelToHourlyListUseCase: HourlyModelToHourlyListUseCase,
//    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
//    private val dataStore: IDataStoreRepo,
//    private val weatherLocationManager: ILocationProvider,
// ) : ViewModel() {
//
//    private var searchJob: Job? = null
//
//    //    private var geonamesJob: Job? = null
//    private val _state = MutableStateFlow(MainPageState())
//
//    val state = _state.onStart {
//        observeSearchQuery()
// //        observeLocationChanges()
//        observeUserSettings()
//        observeUserGeolocationChanges()
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = MainPageState()
//    )
//
//    private fun observeUserSettings() {
//        viewModelScope.launch {
//            dataStore.userSettings.collect { settings ->
//                _state.update {
//                    it.copy(userSettings = settings)
//                }
//            }
//        }
//    }
//
//    fun onIntent(intent: MainPageIntent) {
//        when (intent) {
//            is OnSearchQueryChange -> changeSearchQuery(intent)
//            is OnExpandSearchBar -> onChangeExpandSearchBar(intent)
//            is OnSearchedGeoItemCardClick -> {
//                viewModelScope.launch { onSelectResultCardClick(intent) }
//            }
//
//            is OnGeolocationIconClick -> getGeolocation()
//            else -> Unit
//        }
//    }
//
//    private suspend fun onSelectResultCardClick(intent: OnSearchedGeoItemCardClick) {
//        dataStore.saveUserLocation(
//            latitude = intent.geoItem.latitude,
//            longitude = intent.geoItem.longitude
//        )
//        _state.update {
//            it.copy(
//                searchExpanded = false,
//                city = intent.geoItem.cityName,
//                country = intent.geoItem.country,
//            )
//        }
//    }
//
//    private fun getGeolocation() {
//        _state.update { it.copy(isGeolocationSearchInProgress = true) }
//        weatherLocationManager.getGeolocationFromGpsSensors(
//            onSuccessfullyLocationReceived = { lat, lon ->
//                viewModelScope.launch {
//                    dataStore.saveUserLocation(
//                        latitude = lat,
//                        longitude = lon
//                    )
//                }
//                _state.update {
//                    it.copy(isGeolocationSearchInProgress = false)
//                }
//            },
//            onError = {}
//        )
//    }
//
//    private fun onChangeExpandSearchBar(intent: OnExpandSearchBar) {
//        _state.update { it.copy(searchExpanded = intent.expandValue) }
//    }
//
//    private fun changeSearchQuery(intent: OnSearchQueryChange) {
//        _state.update { it.copy(searchQuery = intent.query) }
//    }
//
//    @OptIn(FlowPreview::class)
//    private fun observeSearchQuery() {
//        state
//            .map { it.searchQuery }
//            .distinctUntilChanged()
//            .debounce(500L)
//            .onEach { query ->
//                when {
//                    query.isBlank() -> {
//                        _state.update { it.copy(searchResults = emptyList()) }
//                    }
//
//                    query.length >= 2 -> {
//                        searchJob?.cancel()
//                        searchJob = searchLocation(query)
//                    }
//                }
//            }
//            .launchIn(viewModelScope)
//    }
//
//    private fun searchLocation(query: String) = viewModelScope.launch {
//        _state.update {
//            it.copy(isLoading = true)
//        }
//        masterRepository.getSearchedLocation(query, language = "ru")
//            .onSuccess { searchResults ->
//                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        searchResults = searchResults.results
//                    )
//                }
//            }
//            .onError { error ->
//                _state.update {
//                    it.copy(
//                        searchResults = emptyList(),
//                        isLoading = false,
//                    )
//                }
//            }
//    }
//
//    //    private fun observeLocationChanges() {
// //        state.map { it.userSettings }
// //            .distinctUntilChanged()
// //            .onEach { }
// //            .launchIn(viewModelScope)
// //    }
//    private fun observeUserGeolocationChanges() {
//        state.map { it.userSettings.location.latitude }
//            .filter { it != 0.0 }
//            .distinctUntilChanged()
//            .onEach {
//                searchReverseGeoLocation()
//                getCurrentWeather()
//            }
//            .launchIn(viewModelScope)
//    }
//
//    private fun searchReverseGeoLocation() = viewModelScope.launch {
//        _state.update {
//            it.copy(isLoading = true)
//        }
//        masterRepository.getReverseGeocodingLocation(
//            latitude = state.value.userSettings.location.latitude,
//            longitude = state.value.userSettings.location.longitude,
//            language = "ru"
//        ).onSuccess { searchResults ->
//            _state.update {
//                it.copy(
//                    isLoading = false,
//                    city = searchResults.geonames.first().name
//                )
//            }
//        }.onError { error ->
//            _state.update {
//                it.copy(
//                    city = "",
//                    isLoading = false,
//                )
//            }
//        }
//    }
//
//    private fun getCurrentWeather() {
//        viewModelScope.launch {
//            masterRepository.getCurrentWeather(
//                latitude = state.value.userSettings.location.latitude,
//                longitude = state.value.userSettings.location.longitude
//            )
//                .onError {
//                    Log.d(TAG, "error: $it")
//                }
//                .onSuccess { response ->
//                    _state.update {
//                        it.copy(
//                            isLoading = false,
//                            currentWeather = response.asUICurrentWeather(
//                                hourlyModelToHourlyListUseCase = hourlyModelToHourlyListUseCase,
//                                convertDateUseCase = convertDateUseCase,
//                                convertWeatherCodeToEnumUseCase = convertWeatherCodeToEnumUseCase,
//                                convertUnixTimeUseCase = convertUnixTimeUseCase,
//                                formatWindDirectionUseCase = formatWindDirectionUseCase
//                            )
//                        )
//                    }
//                }
//        }
//    }
//
//    companion object {
//        private const val TAG = "MainPageViewModel"
//    }
//
//    override fun onCleared() {
//        searchJob?.cancel()
// //        geonamesJob?.cancel()
//    }
// }
