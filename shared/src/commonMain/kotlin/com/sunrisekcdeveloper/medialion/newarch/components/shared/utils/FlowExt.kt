package com.sunrisekcdeveloper.medialion.newarch.components.shared.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform

fun <T> infiniteFlowOf(generator: (Int) -> T): Flow<T> = flow {
    var counter = 0
    while (true) {
        emit(generator(++counter))
    }
}

fun <T> T.wrapInList(): List<T> = listOf(this)

fun <T> Flow<List<T>>.unwrapList(): Flow<T> = transform { value ->
    flow { value.forEach { emit(it) } }
        .onEach { emit(it) }
}