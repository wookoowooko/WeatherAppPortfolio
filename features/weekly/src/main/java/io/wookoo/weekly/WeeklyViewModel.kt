package io.wookoo.weekly

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WeeklyViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(WeeklyViewModelContract.WeeklyState())
    val state = _state.asStateFlow()

    fun onIntent(intent: WeeklyViewModelContract.OnIntent) {
        when (intent) {
            else -> Unit
        }
    }
}

object WeeklyViewModelContract {
    data class WeeklyState(
        val id: Long = 1,
    )

    sealed interface OnIntent
}
