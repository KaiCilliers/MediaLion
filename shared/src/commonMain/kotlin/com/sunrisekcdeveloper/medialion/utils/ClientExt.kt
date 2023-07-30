package com.sunrisekcdeveloper.medialion.utils

import com.sunrisekcdeveloper.medialion.oldArch.data.NetworkConstants
import io.ktor.http.ParametersBuilder
import kotlin.coroutines.cancellation.CancellationException

fun ParametersBuilder.standardParameters() {
    append(NetworkConstants.FIELD_API_KEY, "9b3b6234bb46dbbd68fedc64b4d46e63")
    append(NetworkConstants.FIELD_LANGUAGE, "en-US")
    append(NetworkConstants.FIELD_INCLUDE_ADULT, "false")
}

inline fun <T, R> T.runReThrowable(msg: String, block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Exception) {
        Result.failure(Exception(msg, e))
    }
}

/**
 * Attempts [block], returning a successful [Result] if it succeeds, otherwise a [Result.Failure]
 * taking care not to break structured concurrency
 *
 * @see <a href=" https://github.com/Kotlin/kotlinx.coroutines/issues/1814">kotlinx.coroutines discussion</a>
 */
suspend fun <T, R> T.suspendRunReThrowable(msg: String, block: suspend T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (exception: Exception) {
        Result.failure(Exception(msg, exception))
    }
}
