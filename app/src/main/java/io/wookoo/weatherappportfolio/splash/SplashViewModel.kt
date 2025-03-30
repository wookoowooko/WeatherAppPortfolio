package io.wookoo.weatherappportfolio.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.weatherappportfolio.navigation.MainGraph
import io.wookoo.weatherappportfolio.navigation.WelcomeGraph
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStore: IDataStoreRepo,
) : ViewModel() {

    val splashState: StateFlow<SplashState> = dataStore.userSettings
        .map { settings ->
            Log.d(TAG, "$settings")
            if (settings.windSpeedUnit.isNotEmpty() &&
                settings.temperatureUnit.isNotEmpty() &&
                settings.precipitationUnit.isNotEmpty()
            ) {
                if (settings.isLocationChoose) {
                    SplashState.Success(startDestination = MainGraph)
                } else {
                    SplashState.Success(startDestination = WelcomeGraph)
                }
            } else {
                dataStore.setInitialWeatherUnits()
                SplashState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = SplashState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    companion object {
        private const val TAG = "SplashViewModel"
    }
}
