package io.wookoo.domain.model.settings

import io.wookoo.domain.units.WeatherUnit
import java.util.Locale

data class UserSettingsModel(
    val isLocationChoose: Boolean = false,
    val locale: String = Locale.getDefault().language.lowercase(),
    val temperatureUnit: String = WeatherUnit.Undefined.toString(),
    val windSpeedUnit: String = WeatherUnit.Undefined.toString(),
    val precipitationUnit: String = WeatherUnit.Undefined.toString()
)


