package com.example.medialion.local

import com.example.medialion.database.MediaLionDatabase
import com.squareup.sqldelight.ColumnAdapter
import database.MovieCache
import database.TVShowCache

class MediaLionDatabaseFactory(
    private val driver: DatabaseDriverFactory
) {
    fun create(): MediaLionDatabase {
        return MediaLionDatabase(
            driver = driver.create(),
            MovieCacheAdapter = MovieCache.Adapter(genre_idsAdapter = listOfStringsAdapter),
            TVShowCacheAdapter = TVShowCache.Adapter(genre_idsAdapter = listOfStringsAdapter),

        )
    }
}

private val listOfStringsAdapter = object : ColumnAdapter<List<Int>, String> {
    override fun decode(databaseValue: String) =
        if (databaseValue.isEmpty()) {
            listOf()
        } else {
            databaseValue.split(",")
                .map { it.toInt() }
        }
    override fun encode(value: List<Int>) = value.joinToString(separator = ",")
}