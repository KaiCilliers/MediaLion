package com.example.medialion.domain.components.search.usecases

import com.example.medialion.data.repos.MovieRepository
import com.example.medialion.data.repos.TVRepository
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.MediaItem
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.TVShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

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
                }
        }
    }
}