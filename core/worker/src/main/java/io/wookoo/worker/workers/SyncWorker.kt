package io.wookoo.worker.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.sync.ISynchronizer
import io.wookoo.domain.utils.AppResult
import io.wookoo.worker.utils.Constraints
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val synchronizer: ISynchronizer,
    currentForecast: ICurrentForecastRepo,
) : CoroutineWorker(appContext, workerParams) {

    private val weatherList: Flow<List<Long>> = currentForecast.getCurrentForecastGeoItemIds()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val geoItemIds = weatherList.first()
        var syncedSuccessfully = true

        if (geoItemIds.isEmpty()) {
            return@withContext Result.failure()
        }

        geoItemIds.forEach { geoItemId ->
            val syncResults = awaitAll(
                async { synchronizer.syncCurrentForecastFromAPIAndSaveToCache(geoItemId) },
                async {
                    synchronizer.syncWeeklyForecastFromAPIAndSaveToCache(
                        geoItemId
                    )
                }
            )

            if (syncResults.any { it is AppResult.Error }) {
                syncedSuccessfully = false
            }
        }
        if (syncedSuccessfully) {
            return@withContext Result.success()
        } else {
            return@withContext Result.retry()
        }
    }

    internal companion object {

        private const val FORCE_ONE_TIME_SYNC_TAG = "ForceOneTimeForecastSync"
        private const val PERIODIC_SYNC_WORK_TAG = "PeriodicTimeForecastSync"
        private const val PERIODIC_DURATION = 1L

        fun forceSyncOneTimeTask(): OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(Constraints.syncConstraints)
                .addTag(FORCE_ONE_TIME_SYNC_TAG)
                .build()

        fun startSyncWeatherPeriodically(): PeriodicWorkRequest {
            val now = ZonedDateTime.now(ZoneId.systemDefault())
            val nextHour = now.truncatedTo(ChronoUnit.HOURS).plusHours(1)
            val delayMillis = ChronoUnit.MILLIS.between(now, nextHour)

            return PeriodicWorkRequest.Builder(
                SyncWorker::class.java,
                PERIODIC_DURATION,
                TimeUnit.HOURS
            )
                .setConstraints(Constraints.syncConstraints)
                .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                .addTag(PERIODIC_SYNC_WORK_TAG)
                .build()
        }
    }
}
