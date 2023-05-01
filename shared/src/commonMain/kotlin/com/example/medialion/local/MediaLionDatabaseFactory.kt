package com.example.medialion.local

import com.example.medialion.database.MediaLionDatabase
import com.squareup.sqldelight.ColumnAdapter
import database.MovieDetailEntity
import database.MovieEntity
import database.TvShowEntity

class MediaLionDatabaseFactory(
    private val driver: DatabaseDriverFactory
) {
    fun create(): MediaLionDatabase {
        return MediaLionDatabase(
            driver = driver.create(),
            movieEntityAdapter = MovieEntity.Adapter(listOfStringsAdapter),
            movieDetailEntityAdapter = MovieDetailEntity.Adapter(listOfStringsAdapter),
            tvShowEntityAdapter = TvShowEntity.Adapter(listOfStringsAdapter)
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