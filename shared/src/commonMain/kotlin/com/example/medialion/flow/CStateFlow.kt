package com.example.medialion.flow

import kotlinx.coroutines.flow.StateFlow

expect open class CStateFlow<T>(flow: StateFlow<T>) : StateFlow<T>

fun <T> StateFlow<T>.cStateFlow(): CStateFlow<T> = CStateFlow(this)