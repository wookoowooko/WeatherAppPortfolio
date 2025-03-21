package io.wookoo.main.mvi

import android.util.Log
import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.onError
import io.wookoo.domain.utils.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPageStore @Inject constructor(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    private val masterRepository: IMasterWeatherRepo,
    reducer: MainPageReducer,
) : Store<MainPageState, MainPageIntent, MainPageEffect>(
    storeScope = storeScope,
    initialState = MainPageState(),
    reducer = reducer,
) {
    private var searchJob: Job? = null
    private var synchronizeJob: Job? = null

    override fun initializeObservers() {
        observeSearchQuery()
        viewPagerCount
        observeCurrentWeather()
//        observePagerPosition()
    }

//    private fun observePagerPosition() {
//        state.map { it.pagerPosition }
//            .distinctUntilChanged()
//            .onEach { synchronizeWeather() }
//            .launchIn(storeScope)
//    }

    /** Observers */

    private val viewPagerCount: StateFlow<List<Long>> = masterRepository.getCurrentWeatherIds()
        .filterNotNull()
        .distinctUntilChanged()
        .onEach {
            Log.d(TAG, "listSize: ${it.size}")
            dispatch(UpdateCityListCount(it.size))
        }
        .stateIn(
            storeScope,
            SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeCurrentWeather() {
        viewPagerCount
            .filter { it.isNotEmpty() }
            .flatMapLatest { geoNameIds ->
                state.map { it.pagerPosition }
                    .distinctUntilChanged()
                    .flatMapLatest { position ->
                        masterRepository.currentWeather(geoNameIds[position])
                    }
            }
            .onEach { currentWeather ->
                dispatch(OnSuccessFetchCurrentWeatherFromApi(currentWeather))
            }
            .launchIn(storeScope)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(THRESHOLD)
            .onEach { query ->
                if (query.length >= 2) {
                    searchJob?.cancel()
                    searchJob = searchLocationFromApi(query)
                } else {
                    dispatch(OnQueryIsEmpty)
                }
            }
            .launchIn(storeScope)
    }

    /** Functions */

    private fun searchLocationFromApi(query: String) = storeScope.launch {
        dispatch(OnLoading)
        masterRepository.searchLocation(query, language = "ru")
            .onSuccess { searchResults ->
                Log.d(TAG, "searchLocationFromApi: $searchResults")
                dispatch(OnSuccessSearchLocation(results = searchResults.results))
            }
            .onError { error ->
                dispatch(OnErrorSearchLocation)
                emitSideEffect(MainPageEffect.OnShowSnackBar(error))
            }
    }

//    private fun synchronizeWeather() {
//        synchronizeJob?.cancel()
//
//        synchronizeJob = storeScope.launch {
//            val geoNameIds = viewPagerCount.value
//            val position = state.value.pagerPosition
//
//            if (geoNameIds.isNotEmpty() && position in geoNameIds.indices) {
//                Log.d(TAG, "synchronizeWeatherOnStart started for position $position")
//                masterRepository.synchronizeCurrentWeather(geoNameIds[position])
//                    .onError { syncError ->
//                        emitSideEffect(MainPageEffect.OnShowSnackBar(syncError))
//                    }
//            }
//        }
//    }

    private companion object {
        private const val THRESHOLD = 500L
        private const val TAG = "MainPageStore"
    }
}
