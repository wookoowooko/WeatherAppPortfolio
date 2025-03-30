package io.wookoo.settings.mvi

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsPageViewModel @Inject constructor(
    private val store: SettingsStore,
) : ViewModel() {
    val state = store.createState()
    fun onIntent(intent: SettingsIntent) = store.dispatch(intent)
}
