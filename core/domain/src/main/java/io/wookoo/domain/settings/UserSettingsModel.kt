package io.wookoo.domain.settings

data class UserSettingsModel(
    val location: UserLocationModel = UserLocationModel(),
    val isLocationChoose: Boolean? = null,
    val lastGeoName: Long = 0L
)
