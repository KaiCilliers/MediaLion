package com.sunrisekcdeveloper.medialion.storage

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(MediaLionDatabase.Schema, context, "MediaLion.db")
    }
}