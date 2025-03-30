package io.wookoo.main.mvi

import io.wookoo.common.mvi.Reducer
import javax.inject.Inject

class MainPageReducer @Inject constructor() : Reducer<MainPageState, MainPageIntent> {

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
                                currentWeather = intent.cachedResult
                            )
                        }

                        else -> it
                    }
                }

            else -> state
        }
    }
}
