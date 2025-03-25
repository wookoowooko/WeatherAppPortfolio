package io.wookoo.worker.utils

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import io.wookoo.worker.workers.OneTimeSyncWorker
import io.wookoo.worker.workers.SyncWorker

object Sync {

    fun initializeOneTime(context: Context, locationId: Long, isNeedToUpdate: Boolean) {
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                ONETIME_SYNC_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeSyncWorker.startUpSyncWork(locationId, isNeedToUpdate)
            )
    }

    fun initializePeriodic(context: Context) {
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                PERIODIC_SYNC_WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                SyncWorker.startSyncWeatherPeriodically()
            )
    }

//    fun initializeReSync(context: Context) {
//        WorkManager.getInstance(context).apply {
//            enqueueUniqueWork(
//                ONETIME_RE_SYNC_WORK_NAME,
//                ExistingWorkPolicy.KEEP,
//                PeriodicSyncForecastWorker.startUpHardRsSyncWork()
//            )
//        }
//    }
}

internal const val ONETIME_SYNC_WORK_NAME = "OneTimeSyncWork"

// internal const val ONETIME_RE_SYNC_WORK_NAME = "OneTimeReSyncWork"
internal const val PERIODIC_SYNC_WORK_NAME = "PeriodicSyncWork"
