package io.wookoo.network.extensions

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import retrofit2.Response


inline fun <reified T> responseToResult(
    response: Response<T>,
): AppResult<T, DataError.Remote> {
    return when {
        response.isSuccessful -> {
            try {
                val body = response.body()
                body?.let { AppResult.Success(it) } ?: AppResult.Error(DataError.Remote.SERIALIZATION)
            } catch (e: Exception) {
                AppResult.Error(DataError.Remote.SERIALIZATION)
            }
        }

        response.code() == 408 -> AppResult.Error(DataError.Remote.REQUEST_TIMEOUT)
        response.code() == 429 -> AppResult.Error(DataError.Remote.TOO_MANY_REQUESTS)
        response.code() in 500..599 -> AppResult.Error(DataError.Remote.SERVER)
        else -> AppResult.Error(DataError.Remote.UNKNOWN)
    }
}

