package io.wookoo.domain.settings

data class UserSettingsModel(
    val location: UserLocationModel = UserLocationModel(),
    val isLocationChoose: Boolean? = null,
)
