package com.sunrisekcdeveloper.medialion.local

import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(MediaLionDatabase.Schema, "MediaLion.db")
    }
}