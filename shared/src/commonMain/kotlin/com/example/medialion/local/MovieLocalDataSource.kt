package com.example.medialion.local

import com.example.medialion.database.MediaLionDatabase
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieDetail
import com.squareup.sqldelight.runtime.coroutines.asFlow
import database.MediaListEntity
import database.MovieDetailEntity
import database.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface MovieLocalDataSource {
    suspend fun movieDetails(id: Int): MovieDetail?
    suspend fun addDetailedMovie(entity: MovieDetailEntity)
    fun moviesForList(listName: String): Flow<Movie>
    suspend fun addMovieToList(listName: String, movie: MovieEntity)
    class Default(
        private val mediaLionDb: MediaLionDatabase,
        private val movieDetailMapper: Mapper<MovieDetailEntity, MovieDetail>,
        private val movieMapper: Mapper<MovieEntity, Movie>,
    ) : MovieLocalDataSource {
        val s = mediaLionDb.groupedMoviesXREFQueries
            .moviesFromList("")
            .asFlow()
            .map { it.executeAsOneOrNull() }

        override suspend fun movieDetails(id: Int): MovieDetail? {
            val entity = mediaLionDb.movieDetailQueries.movie(id).executeAsOneOrNull()
            return if (entity != null) movieDetailMapper.map(entity) else null
        }

        override suspend fun addDetailedMovie(entity: MovieDetailEntity) {
            mediaLionDb.movieDetailQueries.insert(entity)
        }

        override suspend fun addMovieToList(listName: String, movie: MovieEntity) {
            mediaLionDb.transaction {
                mediaLionDb.mediaListQueries.insert(listName)
                mediaLionDb.movieQueries.insert(movie)
                mediaLionDb.groupedMoviesXREFQueries.insert(listName, movie.id)
            }
        }

        override fun moviesForList(listName: String): Flow<Movie> {
            return mediaLionDb.groupedMoviesXREFQueries
                .moviesFromList(listName)
                .asFlow()
                .map { movieMapper.map(it.executeAsOne()) }
        }
    }
}

