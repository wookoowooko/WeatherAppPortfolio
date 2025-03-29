package io.wookoo.main.mvi

import io.wookoo.domain.utils.AppError
import io.wookoo.models.ui.UiCurrentWeatherModel

sealed interface MainPageIntent

interface Completable : MainPageIntent

// Success Completable
data class OnGetCurrentForecast(val cachedResult: UiCurrentWeatherModel) :
    Completable

// object MainPageIntent
data object OnLoading : MainPageIntent
data class OnNavigateToWeekly(val geoItemId: Long) :
    MainPageIntent

data object OnNavigateToCities : MainPageIntent
data object OnNavigateToSettings : MainPageIntent

// data MainPageIntent
data class UpdateCityListCount(val count: Int) : MainPageIntent
data class SetPagerPosition(val position: Int) : MainPageIntent

sealed interface MainPageEffect {
    data class OnShowSnackBar(val message: AppError) : MainPageEffect
    data object OnShowSettingsDialog : MainPageEffect
}
