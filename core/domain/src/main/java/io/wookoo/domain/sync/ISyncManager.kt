package io.wookoo.domain.sync

import kotlinx.coroutines.flow.Flow

interface ISyncManager {
    val syncChannel: Flow<Unit>
}
