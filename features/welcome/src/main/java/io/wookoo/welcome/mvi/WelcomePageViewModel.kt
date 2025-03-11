package io.wookoo.welcome.mvi

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomePageViewModel @Inject constructor(
    private val store: WelcomePageStore,
) : ViewModel() {
    val state = store.createState()
    val sideEffect = store.createSideEffect()
    fun onIntent(intent: WelcomePageIntent) = store.dispatch(intent)

    override fun onCleared() {
        store.clear()
    }
}
