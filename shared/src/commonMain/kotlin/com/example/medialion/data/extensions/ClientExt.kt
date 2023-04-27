package com.example.medialion.data.extensions

import com.example.medialion.data.NetworkConstants
import com.example.medialion.domain.models.ResultOf
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ParametersBuilder
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

fun ParametersBuilder.standardParameters() {
    append(
        NetworkConstants.FIELD_API_KEY,
        "9b3b6234bb46dbbd68fedc64b4d46e63"
    )
    append(
        NetworkConstants.FIELD_LANGUAGE,
        "en-US"
    )
    append(
        NetworkConstants.FIELD_INCLUDE_ADULT,
        "false"
    )
}

suspend inline fun <reified T> CoroutineDispatcher.safeApiCall(crossinline request: suspend () -> HttpResponse): ResultOf<T> =
    withContext(this) {
        return@withContext try {
            val response: T = request().body()
            ResultOf.Success(response)
        } catch (ioe: IOException) {
            println("deadpool - ${ioe.cause}")
            ResultOf.Failure("[IO] error please retry", ioe)
        } catch (ex: Exception) {
            println("deadpool - ${ex.cause}")
            ResultOf.Failure("[Exception] error please retry", ex)
        }
    }
