package com.example.medialion.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newFixedThreadPoolContext

actual class DefaultDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    /*
    See https://github.com/Kotlin/kotlinx.coroutines/commit/a38ed39e10cdc2123192ead0727e137cb4969601
    https://github.com/Kotlin/kotlinx.coroutines/issues/3205
     */
    override val io: CoroutineDispatcher
        get() = newFixedThreadPoolContext(nThreads = 64, name = "Dispatchers.IO")
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}