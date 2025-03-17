package io.wookoo.cities.mvi

import io.wookoo.domain.utils.AppError

sealed interface CitiesSideEffect {
    data class ShowSnackBar(val message: AppError) : CitiesSideEffect
}
