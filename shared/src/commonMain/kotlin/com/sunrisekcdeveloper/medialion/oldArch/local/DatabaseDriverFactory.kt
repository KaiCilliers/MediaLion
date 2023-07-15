package com.sunrisekcdeveloper.medialion.oldArch.local

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}