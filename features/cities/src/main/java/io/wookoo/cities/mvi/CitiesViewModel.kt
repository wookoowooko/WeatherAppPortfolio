package io.wookoo.cities.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val store: CitiesStore,
) : ViewModel() {
    val state = store.createState()
    val sideEffect = store.createSideEffect()
    fun onIntent(intent: CitiesIntent) = store.dispatch(intent)

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        store.clear()
    }
    private companion object {
        private const val TAG = "CitiesViewModel"
    }
}
