package io.wookoo.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.enums.UpdateIntent
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.AppResult
import io.wookoo.worker.SyncWeather.SYNC_WORK_TAG
import io.wookoo.worker.SyncWeather.SyncConstraints
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
    private val repo: IMasterWeatherRepo,
) : CoroutineWorker(appContext, workerParams) {

    private val weatherList: Flow<List<Long>> = repo.getCurrentWeatherIds()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val geoItemIds = weatherList.first()
        var syncedSuccessfully = true

        geoItemIds.forEach { geoItemId ->
            val syncResults = awaitAll(
                async { repo.synchronizeCurrentWeather(geoItemId) },
                async {
                    repo.syncWeeklyWeatherFromAPIAndSaveToCache(
                        geoItemId,
                        updateIntent = UpdateIntent.FROM_WORKER
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

        private const val PERIODIC_DURATION = 1L

        fun startSyncWeatherPeriodically(): PeriodicWorkRequest {
            val now = ZonedDateTime.now(ZoneId.systemDefault())
            val nextHour = now.truncatedTo(ChronoUnit.HOURS).plusHours(1)
            val delayMillis = ChronoUnit.MILLIS.between(now, nextHour)

            return PeriodicWorkRequest.Builder(
                SyncWorker::class.java,
                PERIODIC_DURATION,
                TimeUnit.HOURS // Повторение раз в час
            )
                .setConstraints(SyncConstraints)
                .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS) // Только для первого запуска!
                .addTag(SYNC_WORK_TAG)
                .build()
        }
    }
}

object SyncWeather {
    fun initialize(context: Context) {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            SyncWorker.startSyncWeatherPeriodically()
        )
    }

    internal val SyncConstraints
        get() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

    private const val SYNC_WORK_NAME = "SyncWorkName"
    const val SYNC_WORK_TAG = "SyncWorkTag"
}
