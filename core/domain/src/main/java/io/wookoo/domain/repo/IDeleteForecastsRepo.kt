package io.wookoo.domain.repo

import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.EmptyResult

interface IDeleteForecastsRepo {
    suspend fun deleteCityWithCurrentAndWeeklyForecasts(geoItemId: Long): EmptyResult<DataError.Local>
}
