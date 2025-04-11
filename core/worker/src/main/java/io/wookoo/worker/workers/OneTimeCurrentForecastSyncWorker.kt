package io.wookoo.worker.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.repo.widgets.ICurrentForecastGlanceWidget
import io.wookoo.domain.sync.ISynchronizer
import io.wookoo.domain.utils.AppResult
import io.wookoo.worker.utils.Constraints
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

@HiltWorker
class OneTimeCurrentForecastSyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val synchronizer: ISynchronizer,
    private val currentForecast: ICurrentForecastRepo,
    private val currentForecastGlanceWidget: ICurrentForecastGlanceWidget,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val geoItemId = inputData.getLong(KEY_GEO_ITEM_ID, DEFAULT_VALUE_GEO)
        val isNeedToUpdate =
            inputData.getBoolean(KEY_IS_NEED_TO_UPDATE, DEFAULT_VALUE_IS_NEED_TO_UPDATE)

        if (geoItemId == DEFAULT_VALUE_GEO) return@withContext Result.failure()

        val syncResults = awaitAll(
            async { synchronizer.syncCurrentForecastFromAPIAndSaveToCache(geoItemId) },
            async {
                synchronizer.syncWeeklyForecastFromAPIAndSaveToCache(
                    geoItemId
                )
            }
        )

        if (syncResults.any { it is AppResult.Error }) {
            return@withContext Result.retry()
        }

        if (isNeedToUpdate) {
            val updated = currentForecast.updateCurrentForecastLocation(geoItemId)
            if (updated is AppResult.Error) {
                return@withContext Result.retry()
            }
        }
        currentForecastGlanceWidget.forecastSynchronized()
        Result.success()
    }

    internal companion object {
        private const val KEY_GEO_ITEM_ID = "geoItemId"
        private const val KEY_IS_NEED_TO_UPDATE = "isNeedToUpdate"
        private const val DEFAULT_VALUE_GEO: Long = -1L
        private const val DEFAULT_VALUE_IS_NEED_TO_UPDATE: Boolean = false
        private const val ONE_TIME_SYNC_TAG = "OneTimeForecastSync"

        fun startUpSyncWork(geoItemId: Long, isNeedToUpdate: Boolean = false) =
            OneTimeWorkRequestBuilder<OneTimeCurrentForecastSyncWorker>()
                .setInputData(
                    workDataOf(
                        KEY_GEO_ITEM_ID to geoItemId,
                        KEY_IS_NEED_TO_UPDATE to isNeedToUpdate
                    )
                )
                .setConstraints(Constraints.syncConstraints)
                .addTag(ONE_TIME_SYNC_TAG)
                .build()
    }
}
