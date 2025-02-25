package io.wookoo.network.extensions

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import retrofit2.Response

inline fun <reified T> responseToResult(
    response: Response<T>,
): AppResult<T, DataError.Remote> {
    return if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            AppResult.Success(body)
        } else {
            AppResult.Error(DataError.Remote.SERIALIZATION)
        }
    } else {
        when (response.code()) {
            408 -> AppResult.Error(DataError.Remote.REQUEST_TIMEOUT)
            429 -> AppResult.Error(DataError.Remote.TOO_MANY_REQUESTS)
            in 500..599 -> AppResult.Error(DataError.Remote.SERVER)
            else -> AppResult.Error(DataError.Remote.UNKNOWN)
        }
    }
}
