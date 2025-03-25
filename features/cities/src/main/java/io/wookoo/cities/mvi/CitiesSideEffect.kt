package io.wookoo.cities.mvi

import io.wookoo.domain.utils.AppError

sealed interface CitiesSideEffect {
    data class ShowSnackBar(val message: AppError) : CitiesSideEffect
    data class OnShowSettingsDialog(val message: AppError) : CitiesSideEffect
    data class OnSyncRequest(val geoItemId: Long, val isNeedToUpdate: Boolean = false) : CitiesSideEffect
}
