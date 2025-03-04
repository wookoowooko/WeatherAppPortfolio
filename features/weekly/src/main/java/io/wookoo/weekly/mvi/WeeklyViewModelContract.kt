package io.wookoo.weekly.mvi

object WeeklyViewModelContract {
    data class WeeklyState(
        val id: Long = 1,
    )

    sealed interface OnIntent
}
