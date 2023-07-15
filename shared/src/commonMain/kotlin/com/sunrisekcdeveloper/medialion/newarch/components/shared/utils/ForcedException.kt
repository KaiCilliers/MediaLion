package com.sunrisekcdeveloper.medialion.newarch.components.shared.utils

class ForcedException(
    msg: String = "Forced a Failure",
    throwable: Throwable? = null
) : Exception(msg, throwable)