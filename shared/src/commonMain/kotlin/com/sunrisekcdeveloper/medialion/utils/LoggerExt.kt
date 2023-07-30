package com.sunrisekcdeveloper.medialion.utils

import io.github.aakira.napier.Napier

fun Napier.debug(msg: () -> String) {
    this.d(tag = "deadpool") { msg() }
}

fun debug(msg: () -> String) {
    Napier.debug { msg() }
}