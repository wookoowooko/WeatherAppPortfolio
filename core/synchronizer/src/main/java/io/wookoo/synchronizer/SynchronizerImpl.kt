package io.wookoo.synchronizer

import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.repo.IWeeklyForecastRepo
import io.wookoo.domain.sync.ISynchronizer
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import javax.inject.Inject

class SynchronizerImpl @Inject constructor(
    private val weeklyRepo: IWeeklyForecastRepo,
    private val currentRepo: ICurrentForecastRepo,
) : ISynchronizer {

    override suspend fun syncWeeklyWeatherFromAPIAndSaveToCache(
        geoItemId: Long,
    ): AppResult<Unit, DataError> {
        return weeklyRepo.sync(geoItemId)
    }

    override suspend fun synchronizeCurrentWeatherFromAPIAndSaveToCache(
        geoItemId: Long,
    ): AppResult<Unit, DataError> {
        return currentRepo.sync(geoItemId)
    }
}
