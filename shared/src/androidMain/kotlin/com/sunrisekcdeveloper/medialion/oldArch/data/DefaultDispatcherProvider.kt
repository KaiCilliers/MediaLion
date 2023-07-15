package com.sunrisekcdeveloper.medialion.oldArch.data

import com.sunrisekcdeveloper.medialion.oldArch.data.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class DefaultDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}