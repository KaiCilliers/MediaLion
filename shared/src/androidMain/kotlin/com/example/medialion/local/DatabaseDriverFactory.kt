package com.example.medialion.local

import android.content.Context
import com.example.medialion.database.MediaLionDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(MediaLionDatabase.Schema, context, "MediaLion.db")
    }
}