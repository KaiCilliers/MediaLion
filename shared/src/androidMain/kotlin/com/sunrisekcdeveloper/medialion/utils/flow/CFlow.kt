package com.sunrisekcdeveloper.medialion.utils.flow

import kotlinx.coroutines.flow.Flow

actual class CFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> by flow