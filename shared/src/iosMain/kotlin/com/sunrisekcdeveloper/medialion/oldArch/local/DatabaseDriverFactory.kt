package com.sunrisekcdeveloper.medialion.oldArch.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(MediaLionDatabase.Schema, "MediaLion.db")
    }
}