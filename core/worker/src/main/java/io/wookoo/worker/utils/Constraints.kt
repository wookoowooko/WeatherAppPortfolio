package io.wookoo.worker.utils

import androidx.work.Constraints
import androidx.work.NetworkType

internal object Constraints {
    val syncConstraints
        get() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
}
