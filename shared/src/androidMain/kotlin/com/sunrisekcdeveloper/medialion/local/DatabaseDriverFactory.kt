package com.sunrisekcdeveloper.medialion.local

import android.content.Context
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(MediaLionDatabase.Schema, context, "MediaLion.db")
    }
}