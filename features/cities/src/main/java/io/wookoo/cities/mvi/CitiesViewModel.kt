package io.wookoo.cities.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.units.ApiUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val masterRepository: IMasterWeatherRepo,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CitiesState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            masterRepository.getAllCitiesCurrentWeather()
                .collect { listOfCities: List<CurrentWeatherResponseModel> ->
                    _state.update {
                        it.copy(
                            cities = listOfCities.map { currentWeather ->
                                currentWeather.asUiCity(
                                    convertWeatherCodeToEnumUseCase = convertWeatherCodeToEnumUseCase
                                )
                            }
                        )
                    }
                }
        }
    }
}

fun CurrentWeatherResponseModel.asUiCity(
    convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
): UiCity {
    return UiCity(
        cityName = this.cityName,
        countryName = this.countryName,
        temperature = WeatherValueWithUnit(
            value = this.current.temperature,
            unit = ApiUnit.CELSIUS
        ),
        temperatureFeelsLike = WeatherValueWithUnit(
            value = this.current.feelsLike,
            unit = ApiUnit.CELSIUS
        ),
        isDay = this.current.isDay,
        weatherStatus = convertWeatherCodeToEnumUseCase(this.current.weatherStatus)

    )
}

data class CitiesState(
    val cities: List<UiCity> = emptyList(),
)

data class UiCity(
    val isDay: Boolean,
    val cityName: String,
    val countryName: String,
    val temperature: WeatherValueWithUnit = WeatherValueWithUnit(),
    val temperatureFeelsLike: WeatherValueWithUnit = WeatherValueWithUnit(),
    val weatherStatus: WeatherCondition = WeatherCondition.UNKNOWN,
)
