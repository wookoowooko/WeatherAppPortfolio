package io.wookoo.welcome.mvi

import io.wookoo.domain.utils.AppError

sealed interface WelcomeSideEffect {
    data class ShowSnackBar(val message: AppError) : WelcomeSideEffect
    data class OnShowSettingsDialog(val message: AppError) : WelcomeSideEffect
}
