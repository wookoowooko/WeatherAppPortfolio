package io.wookoo.settings.mvi

import io.wookoo.common.mvi.Reducer
import javax.inject.Inject

class SettingsPageReducer @Inject constructor() :
    Reducer<SettingsState, SettingsIntent> {

    override fun reduce(
        state: SettingsState,
        intent: SettingsIntent,
    ): SettingsState {
        return when (intent) {
            is UpdateSelectedTemperature -> {
                val dataUnit =
                    state.temperatureUnitOptions.find { it.apiValue == intent.temperatureUnit }
                state.copy(selectedTemperatureUnit = dataUnit ?: state.selectedTemperatureUnit)
            }

            is UpdateSelectedWindSpeed -> {
                val dataUnit =
                    state.windSpeedUnitOptions.find { it.apiValue == intent.windSpeedUnit }
                state.copy(selectedWindSpeedUnit = dataUnit ?: state.selectedWindSpeedUnit)
            }

            is UpdateSelectedPrecipitation -> {
                val dataUnit =
                    state.precipitationUnitOptions.find { it.apiValue == intent.precipitationUnit }
                state.copy(selectedPrecipitationUnit = dataUnit ?: state.selectedPrecipitationUnit)
            }

            else -> state
        }
    }
}
