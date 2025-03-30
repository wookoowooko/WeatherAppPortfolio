package io.wookoo.models.settings

data class UserSettingsModel(
    val isLocationChoose: Boolean,
    val temperatureUnit: String,
    val windSpeedUnit: String,
    val precipitationUnit: String
)
