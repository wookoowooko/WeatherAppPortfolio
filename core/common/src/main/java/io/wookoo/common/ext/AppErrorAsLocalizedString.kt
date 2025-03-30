package io.wookoo.common.ext

import android.content.Context
import io.wookoo.domain.utils.AppError
import io.wookoo.domain.utils.DataError

fun AppError.asLocalizedString(context: Context): String {
    return when (this) {
        DataError.Remote.REQUEST_TIMEOUT -> context.getString(io.wookoo.androidresources.R.string.error_request_timeout)
        DataError.Remote.SERVER -> context.getString(io.wookoo.androidresources.R.string.error_server)
        DataError.Remote.NO_INTERNET -> context.getString(io.wookoo.androidresources.R.string.error_no_internet)
        DataError.Remote.UNKNOWN -> context.getString(io.wookoo.androidresources.R.string.error_unknown)
        DataError.Remote.SERIALIZATION -> context.getString(io.wookoo.androidresources.R.string.error_serialization)
        DataError.Remote.CANT_SYNC -> context.getString(io.wookoo.androidresources.R.string.error_cant_sync)
        DataError.Remote.TOO_MANY_REQUESTS -> context.getString(
            io.wookoo.androidresources.R.string.error_too_many_requests
        )

        DataError.Local.UNKNOWN -> context.getString(io.wookoo.androidresources.R.string.error_unknown)
        DataError.Local.DISK_FULL -> context.getString(io.wookoo.androidresources.R.string.error_disk_full)
        DataError.Local.CANT_DELETE_DATA -> context.getString(
            io.wookoo.androidresources.R.string.error_cannot_delete_data
        )
        DataError.Local.CAN_NOT_SAVE_DATA_TO_DATASTORE -> context.getString(
            io.wookoo.androidresources.R.string.error_cannot_save_data
        )
        DataError.Local.LOCAL_STORAGE_ERROR -> context.getString(
            io.wookoo.androidresources.R.string.local_storage_error
        )

        DataError.Hardware.LOCATION_SERVICE_DISABLED -> context.getString(
            io.wookoo.androidresources.R.string.location_service_disabled
        )

        DataError.Hardware.UNKNOWN -> context.getString(io.wookoo.androidresources.R.string.error_unknown)

        else -> context.getString(io.wookoo.androidresources.R.string.error_unknown)
    }
}
