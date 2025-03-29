package io.wookoo.settings.mvi

interface SettingsIntent

data class UpdateSelectedTemperature(val temperatureUnit: String) : SettingsIntent
data class UpdateSelectedWindSpeed(val windSpeedUnit: String) : SettingsIntent
data class UpdateSelectedPrecipitation(val precipitationUnit: String) : SettingsIntent

data class SaveSelectedTemperature(val temperatureUnit: String) : SettingsIntent
data class SaveSelectedWindSpeed(val windSpeedUnit: String) : SettingsIntent
data class SaveSelectedPrecipitation(val precipitationUnit: String) : SettingsIntent
