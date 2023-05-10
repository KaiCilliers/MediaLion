package com.sunrisekcdeveloper.medialion.data

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.*
import platform.darwin.*
import kotlin.coroutines.CoroutineContext

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
        get() = IODispatcher
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}

@OptIn(InternalCoroutinesApi::class)
private object IODispatcher : CoroutineDispatcher(), Delay {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        val queue = dispatch_get_main_queue()
        dispatch_async(queue) {
            block.run()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun scheduleResumeAfterDelay(
        timeMillis: Long,
        continuation: CancellableContinuation<Unit>
    ) {
        val queue = dispatch_get_main_queue()

        val time = dispatch_time(DISPATCH_TIME_NOW, (timeMillis * NSEC_PER_MSEC.toLong()))
        dispatch_after(time, queue) {
            with(continuation) {
                resumeUndispatched(Unit)
            }
        }
    }
}