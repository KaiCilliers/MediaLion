package com.sunrisekcdeveloper.medialion.utils.flow

import kotlinx.coroutines.flow.StateFlow

actual open class CStateFlow<T> actual constructor(
    private val flow: StateFlow<T>
) : StateFlow<T> by flow