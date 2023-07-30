package com.sunrisekcdeveloper.medialion.components.shared.utils

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

fun <T, R> Flow<List<T>>.mapList(transform: suspend (T) -> R): Flow<List<R>> = transform { value ->
    emit(value.map { transform(it) })
}