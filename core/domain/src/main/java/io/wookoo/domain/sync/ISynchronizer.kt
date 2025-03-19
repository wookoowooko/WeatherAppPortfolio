package io.wookoo.domain.sync

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError

interface ISynchronizer {
    suspend fun synchronizeWeeklyWeather(
        latitude: Double,
        longitude: Double,
        geoItemId: Long,
        cityName: String,
    ): AppResult<Unit, DataError>

    suspend fun synchronizeCurrentWeather(
        latitude: Double,
        longitude: Double,
        geoItemId: Long,
        countryName: String,
        cityName: String,
    ): AppResult<Unit, DataError>
}
