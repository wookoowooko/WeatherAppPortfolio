package io.wookoo.domain.repo

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import kotlinx.coroutines.flow.Flow

interface ILocationProvider {
    fun getGeolocationFromGpsSensors(): Flow<AppResult<Pair<Double, Double>, DataError.Hardware>>
}
