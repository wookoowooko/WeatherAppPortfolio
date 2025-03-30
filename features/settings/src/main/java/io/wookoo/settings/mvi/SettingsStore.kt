package io.wookoo.settings.mvi

import io.wookoo.common.mvi.SimpleStore
import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.repo.IDataStoreRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsStore @Inject constructor(
    reducer: SettingsPageReducer,
    private val dataStore: IDataStoreRepo,
    @StoreViewModelScope private val storeScope: CoroutineScope,
) : SimpleStore<SettingsState, SettingsIntent>(
    initialState = SettingsState(),
    storeScope = storeScope,
    reducer = reducer
) {

    private val settings = dataStore.userSettings
        .onEach { settings ->

            dispatch(UpdateSelectedTemperature(settings.temperatureUnit))
            dispatch(UpdateSelectedWindSpeed(settings.windSpeedUnit))
            dispatch(UpdateSelectedPrecipitation(settings.precipitationUnit))
        }
        .stateIn(
            scope = storeScope,
            started = SharingStarted.Eagerly,
            null
        )

    override fun handleSideEffects(intent: SettingsIntent) {
        when (intent) {
            is SaveSelectedTemperature -> storeScope.launch { dataStore.updateTemperatureUnit(intent.temperatureUnit) }
            is SaveSelectedWindSpeed -> storeScope.launch { dataStore.updateWindSpeedUnit(intent.windSpeedUnit) }
            is SaveSelectedPrecipitation -> storeScope.launch {
                dataStore.updatePrecipitationUnit(
                    intent.precipitationUnit
                )
            }
        }
    }

    override fun initializeObservers() {
        settings
    }
}
