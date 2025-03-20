package io.wookoo.domain.sync

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError

interface ISynchronizer {
    suspend fun synchronizeWeeklyWeather(
        geoItemId: Long,
    ): AppResult<Unit, DataError>

    suspend fun synchronizeCurrentWeather(
        geoItemId: Long,
    ): AppResult<Unit, DataError>
}
