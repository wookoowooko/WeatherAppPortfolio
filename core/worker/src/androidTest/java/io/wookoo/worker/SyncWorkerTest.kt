package io.wookoo.worker

import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.wookoo.worker.workers.SyncWorker
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class SyncWorkerTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val context get() = InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setup() {
        hiltRule.inject()

        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    @Throws(Exception::class)
    fun testForceOneTime() {
        // Create request
        val request = SyncWorker.forceSyncOneTimeTask()

        val workManager = WorkManager.getInstance(context)
        // Get the TestDriver
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)!!

        // Enqueue and wait for result.
        workManager.enqueue(request).result.get()

        // Get WorkInfo and outputData
        val preRunWorkInfo = workManager.getWorkInfoById(request.id).get()

        // Assert
        assertEquals(WorkInfo.State.ENQUEUED, preRunWorkInfo?.state)

        // Tells the testing framework that the constraints have been met
        testDriver.setAllConstraintsMet(request.id)

        val workInfo = workManager.getWorkInfoById(request.id).get()
        assertEquals(WorkInfo.State.RUNNING, workInfo?.state)
    }

    @Test
    @Throws(Exception::class)
    fun testPeriodically() {
        // Create request
        val request = SyncWorker.startSyncWeatherPeriodically()

        val workManager = WorkManager.getInstance(context)
        // Get the TestDriver
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)!!
        // Enqueue and wait for result.
        workManager.enqueue(request).result.get()

        // Get WorkInfo before constraints are met
        val preRunWorkInfo = workManager.getWorkInfoById(request.id).get()
        assertEquals(WorkInfo.State.ENQUEUED, preRunWorkInfo?.state)

        with(testDriver) {
            // Set the initial delay to be met
            setInitialDelayMet(request.id)
            // Set the constraints to be met
            setAllConstraintsMet(request.id)
        }

        // Get the final WorkInfo and check the state
        val workInfo = workManager.getWorkInfoById(request.id).get()
        assertEquals(WorkInfo.State.RUNNING, workInfo?.state)
    }
}
