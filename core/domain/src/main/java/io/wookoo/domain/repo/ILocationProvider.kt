package io.wookoo.domain.repo

import io.wookoo.domain.utils.AppError

interface ILocationProvider {
    fun getGeolocationFromGpsSensors(
        onSuccessfullyLocationReceived: (latitude: Double, longitude: Double) -> Unit,
        onError: (AppError) -> Unit,
    )
}
