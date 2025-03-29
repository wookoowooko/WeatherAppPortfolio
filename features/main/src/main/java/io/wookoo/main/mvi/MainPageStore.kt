package io.wookoo.main.mvi

import io.wookoo.common.mvi.Store
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.usecases.PrepareCurrentForecastUseCase
import io.wookoo.models.weather.current.CurrentWeatherDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MainPageStore @Inject constructor(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    private val currentForecast: ICurrentForecastRepo,
    reducer: MainPageReducer,
    private val prepareCurrentForecastUseCase: PrepareCurrentForecastUseCase,
) : Store<MainPageState, MainPageIntent, MainPageEffect>(
    storeScope = storeScope,
    initialState = MainPageState(),
    reducer = reducer
) {

    override fun initializeObservers() {
        viewPagerCount
        observeCurrentWeather()
    }

    /** Observers */

    private val viewPagerCount: StateFlow<List<Long>> =
        currentForecast.getCurrentForecastGeoItemIds()
            .filterNotNull()
            .distinctUntilChanged()
            .onEach {
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
                        currentForecast.getCurrentForecast(geoNameIds[position])
                    }
            }
            .onEach { currentWeather: io.wookoo.models.weather.current.CurrentWeatherDomain ->
                dispatch(OnGetCurrentForecast((prepareCurrentForecastUseCase(currentWeather))))
            }
            .launchIn(storeScope)
    }
}
