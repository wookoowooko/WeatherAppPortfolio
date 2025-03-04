package io.wookoo.weekly.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeeklyViewModel @Inject constructor(
    private val masterRepository: IMasterWeatherRepo,
) : ViewModel() {

    init {
        viewModelScope.launch {
            masterRepository.getWeeklyWeather(
                latitude = 37.5665,
                longitude = 126.9780,
            ).onSuccess {
                Log.d(TAG, "weekly -> $it")
            }
        }
    }

    private val _state = MutableStateFlow(WeeklyViewModelContract.WeeklyState())
    val state = _state.asStateFlow()

    fun onIntent(intent: WeeklyViewModelContract.OnIntent) {
        when (intent) {
            else -> Unit
        }
    }

    companion object {
        private const val TAG = "WeeklyViewModel"
    }
}
