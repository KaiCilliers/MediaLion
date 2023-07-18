package com.sunrisekcdeveloper.medialion.utils.flow

// wrapper to prevent exposing coroutines api to swift side
interface DisposableHandle : kotlinx.coroutines.DisposableHandle

fun DisposableHandle(block: () -> Unit): DisposableHandle {
    return object : DisposableHandle {
        override fun dispose() {
            block()
        }
    }
}

operator fun DisposableHandle.plus(other: DisposableHandle): DisposableHandle {
    return DisposableHandle {
        this.dispose()
        other.dispose()
    }
}