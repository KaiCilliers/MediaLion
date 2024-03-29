package com.sunrisekcdeveloper.medialion.utils.flow

import kotlinx.coroutines.flow.MutableStateFlow

expect class CMutableStateFlow<T>(flow: MutableStateFlow<T>) : MutableStateFlow<T>

fun <T> MutableStateFlow<T>.cMutableStateFlow(): CMutableStateFlow<T> = CMutableStateFlow(this)