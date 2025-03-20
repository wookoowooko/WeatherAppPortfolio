package io.wookoo.domain.utils

interface AppError

sealed interface DataError : AppError {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        CANT_SYNC,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        CAN_NOT_SAVE_DATA_TO_DATASTORE,
        UNKNOWN
    }

    enum class Hardware : DataError {
        LOCATION_SERVICE_DISABLED,
        UNKNOWN
    }
}
