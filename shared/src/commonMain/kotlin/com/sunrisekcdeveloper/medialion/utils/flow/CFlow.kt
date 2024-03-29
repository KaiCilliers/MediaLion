package com.sunrisekcdeveloper.medialion.utils.flow

import kotlinx.coroutines.flow.Flow

expect class CFlow<T>(flow: Flow<T>): Flow<T>

fun <T> Flow<T>.cFlow(): CFlow<T> = CFlow(this)