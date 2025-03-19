package io.wookoo.domain.sync

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError

interface ISynchronizer {
    suspend fun synchronizeWeeklyWeather(
        latitude: Double,
        longitude: Double,
        geoItemId: Long,
    ): AppResult<Unit, DataError>
}
