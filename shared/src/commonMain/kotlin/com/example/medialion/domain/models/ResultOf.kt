package com.example.medialion.domain.models

// optionally import as dependency
// replace with kotlin Result DUH!
sealed class ResultOf<out T> {
    data class Success<out R>(val value: R): ResultOf<R>()
    data class Failure(
        val message: String?,
        val throwable: Throwable?
    ): ResultOf<Nothing>()
}