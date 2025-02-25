package io.wookoo.weatherappportfolio

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wookoo.domain.repo.ICurrentWeatherRepo
import io.wookoo.domain.utils.onSuccess
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val masterRepo: ICurrentWeatherRepo,
) : ViewModel() {

    init {
        viewModelScope.launch {
            masterRepo.getCurrentWeather().onSuccess {
                Log.d(TAG, "$it")
            }
        }
    }

    companion object {
        private const val TAG = "AppViewModel"
    }
}
