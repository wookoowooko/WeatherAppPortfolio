package io.wookoo.domain.sync

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError

interface ISynchronizer {
    suspend fun syncWeeklyForecastFromAPIAndSaveToCache(
        geoItemId: Long
    ): AppResult<Unit, DataError>

    suspend fun syncCurrentForecastFromAPIAndSaveToCache(
        geoItemId: Long
    ): AppResult<Unit, DataError>
}
