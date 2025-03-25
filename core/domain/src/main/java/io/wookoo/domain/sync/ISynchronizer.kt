package io.wookoo.domain.sync

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError

interface ISynchronizer {
    suspend fun syncWeeklyWeatherFromAPIAndSaveToCache(
        geoItemId: Long
    ): AppResult<Unit, DataError>

    suspend fun synchronizeCurrentWeatherFromAPIAndSaveToCache(
        geoItemId: Long
    ): AppResult<Unit, DataError>
}
