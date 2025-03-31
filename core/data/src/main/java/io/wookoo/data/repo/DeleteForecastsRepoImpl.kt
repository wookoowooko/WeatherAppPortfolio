package io.wookoo.data.repo

import io.wookoo.database.daos.DeleteForecastsDao
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.repo.IDeleteForecastsRepo
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.EmptyResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.sql.SQLException
import javax.inject.Inject

class DeleteForecastsRepoImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val deleteForecastDao: DeleteForecastsDao,
) : IDeleteForecastsRepo {
    override suspend fun deleteCityWithCurrentAndWeeklyForecasts(geoItemId: Long): EmptyResult<DataError.Local> {
        return withContext(ioDispatcher) {
            try {
                deleteForecastDao.deleteCityWithCurrentAndWeeklyForecasts(geoItemId)
                AppResult.Success(Unit)
            } catch (e: SQLException) {
                println(e)
                AppResult.Error(DataError.Local.CANT_DELETE_DATA)
            }
        }
    }
}
