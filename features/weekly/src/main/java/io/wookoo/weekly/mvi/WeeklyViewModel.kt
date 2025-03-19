package io.wookoo.weekly.mvi

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeeklyViewModel @Inject constructor(
    private val store: WeeklyStore,
) : ViewModel() {

    val state = store.createState()
    val sideEffect = store.createSideEffect()
    fun onIntent(intent: WeeklyIntent) = store.dispatch(intent)
}
