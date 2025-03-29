package io.wookoo.domain.model.settings

data class UserSettingsModel(
    val isLocationChoose: Boolean,
    val temperatureUnit: String,
    val windSpeedUnit: String,
    val precipitationUnit: String
)
