package io.wookoo.weatherappportfolio.splash

sealed interface SplashState {
    data object Loading : SplashState

    data class Success(val startDestination: Any) : SplashState

    fun shouldKeepSplashScreen() = this is Loading

    fun startDestination() = (this as? Success)?.startDestination
}
