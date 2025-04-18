package io.wookoo.network.extensions

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(execute: () -> Response<T>)
        : AppResult<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (se: SocketTimeoutException) {
        return AppResult.Error(DataError.Remote.REQUEST_TIMEOUT).also {
            println("SocketTimeoutException$se")
        }
    } catch (ue: UnresolvedAddressException) {
        return AppResult.Error(DataError.Remote.NO_INTERNET).also {
            println("UnresolvedAddressException$ue")
        }
    } catch (ue: UnknownHostException) {
        return AppResult.Error(DataError.Remote.NO_INTERNET).also {
            println("UnknownHostException$ue")
        }
    } catch (se: SerializationException) {
        return AppResult.Error(DataError.Remote.SERIALIZATION).also {
            println("SerializationException$se")
        }
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return AppResult.Error(DataError.Remote.UNKNOWN).also {
            println("Exception$e")
        }
    }
    return responseToResult(response)

}
