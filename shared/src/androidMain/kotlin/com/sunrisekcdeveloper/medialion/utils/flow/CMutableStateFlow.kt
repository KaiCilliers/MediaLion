package com.sunrisekcdeveloper.medialion.utils.flow

import kotlinx.coroutines.flow.MutableStateFlow

actual class CMutableStateFlow<T> actual constructor(
    private val flow: MutableStateFlow<T>
) : MutableStateFlow<T> by flow