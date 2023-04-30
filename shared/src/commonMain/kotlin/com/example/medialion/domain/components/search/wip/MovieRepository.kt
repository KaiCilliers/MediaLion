package com.example.medialion.domain.components.search.wip

import com.example.medialion.domain.components.search.wip.domain.Movie
import com.example.medialion.domain.components.search.wip.domain.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun movieDetails(id: Int): MovieDetail
    fun recommendationsForMovie(id: Int): Flow<Movie>

    class Default(
        private val remoteDataSource: MovieRemoteDataSource
    ) : MovieRepository {
        override suspend fun movieDetails(id: Int): MovieDetail {
            return movieDetails(id)
        }

        override fun recommendationsForMovie(id: Int): Flow<Movie> {
            return remoteDataSource.recommendationsForMovie(id)
        }
    }
}