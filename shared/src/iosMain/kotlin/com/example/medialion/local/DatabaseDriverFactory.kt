package com.example.medialion.local

import com.example.medialion.database.MediaLionDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(MediaLionDatabase.Schema, "MediaLion.db")
    }
}