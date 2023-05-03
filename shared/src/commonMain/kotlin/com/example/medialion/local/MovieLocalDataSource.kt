package com.example.medialion.local

import com.example.medialion.database.MediaLionDatabase
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieDetail
import com.example.medialion.domain.models.TVShow
import com.example.medialion.domain.models.TVShowDetail
import database.MovieEntity
import database.TvShowEntity
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

interface TVLocalDataSource {
    suspend fun tvDetails(id: Int): TVShowDetail?
    suspend fun addDetailedTV(entity: TvShowEntity)

    class Default(
        private val mediaLionDb: MediaLionDatabase,
        private val tvDetailMapper: Mapper<TvShowEntity, TVShowDetail>,
    ) : TVLocalDataSource {
        override suspend fun tvDetails(id: Int): TVShowDetail? {
            val entity = mediaLionDb.tvShowQueries.findTVShow(id).executeAsOneOrNull()
            return if (entity != null) tvDetailMapper.map(entity) else null
        }

        override suspend fun addDetailedTV(entity: TvShowEntity) {
            mediaLionDb.tvShowQueries.insert(entity)
        }
    }
}