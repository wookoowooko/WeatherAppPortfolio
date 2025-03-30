package io.wookoo.weekly.mvi

import io.wookoo.domain.utils.AppError

sealed interface WeeklyEffect {
    data class OnShowSnackBar(val error: AppError) : WeeklyEffect
}
