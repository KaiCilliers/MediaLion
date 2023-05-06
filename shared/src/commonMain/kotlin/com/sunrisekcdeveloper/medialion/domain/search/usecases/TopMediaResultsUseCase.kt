package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.repos.MovieRepository
import com.sunrisekcdeveloper.medialion.repos.TVRepository
import com.sunrisekcdeveloper.medialion.mappers.Mapper
import com.sunrisekcdeveloper.medialion.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.take

interface TopMediaResultsUseCase {
    operator fun invoke(searchQuery: String): Flow<MediaItem>
    class Default(
        private val movieRepo: MovieRepository,
        private val tvRepo: TVRepository,
        private val tvMapper: Mapper<TVShow, MediaItem>,
        private val movieMapper: Mapper<Movie, MediaItem>,
    ) : TopMediaResultsUseCase {
        override fun invoke(searchQuery: String): Flow<MediaItem> {
            val tvShowSearchResults = tvRepo.search(searchQuery)
            val movieSearchResults = movieRepo.search(searchQuery)

            return merge(tvShowSearchResults, movieSearchResults)
                .map {
                    if (it is TVShow) {
                        tvMapper.map(it)
                    } else {
                        movieMapper.map(it as Movie)
                    }
                }.take(30)
        }
    }
}