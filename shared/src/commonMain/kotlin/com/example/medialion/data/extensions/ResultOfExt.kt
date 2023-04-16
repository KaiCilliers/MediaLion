package com.example.medialion.data.extensions

import com.example.medialion.domain.models.ResultOf

inline fun <reified T> ResultOf<T>.doIfFailure(callback: (error: String?, throwable: Throwable?) -> Unit) {
    if (this is ResultOf.Failure) {
        callback(message, throwable)
    }
}

inline fun <reified T> ResultOf<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this is ResultOf.Success) {
        callback(value)
    }
}

inline fun <reified T, reified R> ResultOf<T>.map(transform: (T) -> R): ResultOf<R> {
    return when (this) {
        is ResultOf.Success -> ResultOf.Success(transform(value))
        is ResultOf.Failure -> this
    }
}

inline fun <T> ResultOf<T>.withDefault(value: () -> T): ResultOf.Success<T> {
    return when (this) {
        is ResultOf.Success -> this
        is ResultOf.Failure -> ResultOf.Success(value())
    }
}