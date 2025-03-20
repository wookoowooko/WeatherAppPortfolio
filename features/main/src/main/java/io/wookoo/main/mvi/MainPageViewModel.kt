package io.wookoo.main.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val store: MainPageStore,
) : ViewModel() {
    val state = store.createState()
    val sideEffect = store.createSideEffect()
    fun onIntent(intent: MainPageIntent) = store.dispatch(intent)

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
        store.clear()
    }
    private companion object {
        private const val TAG = "MainPageViewModel"
    }
}
