package com.sunrisekcdeveloper.medialion.newarch.components.shared.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> infiniteFlowOf(generator: (Int) -> T): Flow<T> = flow {
    var counter = 0
    while (true) {
        emit(generator(++counter))
    }
}