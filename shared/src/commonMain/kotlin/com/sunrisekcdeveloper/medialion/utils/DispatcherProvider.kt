package com.sunrisekcdeveloper.medialion.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val main : CoroutineDispatcher
    val default : CoroutineDispatcher
    val io : CoroutineDispatcher
    val unconfined : CoroutineDispatcher
}

expect class DefaultDispatcherProvider : DispatcherProvider
