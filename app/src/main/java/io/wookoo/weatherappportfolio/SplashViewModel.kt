package io.wookoo.weatherappportfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.main.navigation.MainRoute
import io.wookoo.weatherappportfolio.navigation.MainGraph
import io.wookoo.weatherappportfolio.navigation.WelcomeGraph
import io.wookoo.welcome.navigation.WelcomeRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    dataStore: IDataStoreRepo
) : ViewModel() {
    val splashState: StateFlow<SplashState> = dataStore.userSettings.map { settings ->
        if (settings.isLocationChoose) {
            SplashState.Success(startDestination = MainGraph)
        } else {
            SplashState.Success(startDestination = WelcomeGraph)
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = SplashState.Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )
}

sealed interface SplashState {
    data object Loading : SplashState

    data class Success(val startDestination: Any) : SplashState

    fun shouldKeepSplashScreen() = this is Loading

    fun startDestination() = (this as? Success)?.startDestination
}
