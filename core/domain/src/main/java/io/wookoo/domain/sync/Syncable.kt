package io.wookoo.domain.sync

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError

interface Syncable {
    suspend fun sync(geoItemId: Long): AppResult<Unit, DataError>
}
