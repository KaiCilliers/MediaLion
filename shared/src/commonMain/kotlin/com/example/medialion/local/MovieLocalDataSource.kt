package com.example.medialion.local

import com.example.medialion.database.MediaLionDatabase
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieDetail
import database.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    suspend fun movieDetails(id: Int): MovieDetail?
    suspend fun addDetailedMovie(entity: MovieEntity)
    fun moviesForList(listName: String): Flow<Movie>
    suspend fun addMovieToList(listName: String, movie: MovieEntity)
    class Default(
        private val mediaLionDb: MediaLionDatabase,
        private val movieDetailMapper: Mapper<MovieEntity, MovieDetail>,
        private val movieMapper: Mapper<MovieEntity, Movie>,
    ) : MovieLocalDataSource {
        override suspend fun movieDetails(id: Int): MovieDetail? {
            val entity = mediaLionDb.movieQueries.findMovie(id).executeAsOneOrNull()
            return if (entity != null) movieDetailMapper.map(entity) else null
        }

        override suspend fun addDetailedMovie(entity: MovieEntity) {
            mediaLionDb.movieQueries.insert(entity)
        }

        override suspend fun addMovieToList(listName: String, movie: MovieEntity) {
//            mediaLionDb.transaction {
//                mediaLionDb.mediaListQueries.insert(listName)
//                mediaLionDb.movieQueries.insert(movie)
//                mediaLionDb.groupedMoviesXREFQueries.insert(listName, movie.id)
//            }
        }

        override fun moviesForList(listName: String): Flow<Movie> {
//            return mediaLionDb.groupedMoviesXREFQueries
//                .moviesFromList(listName)
//                .asFlow()
//                .map { movieMapper.map(it.executeAsOne()) }
            TODO()
        }
    }
}

