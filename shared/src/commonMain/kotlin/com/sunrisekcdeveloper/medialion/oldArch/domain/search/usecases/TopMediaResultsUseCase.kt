package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.oldArch.mappers.Mapper
import com.sunrisekcdeveloper.medialion.oldArch.repos.MovieRepository
import com.sunrisekcdeveloper.medialion.oldArch.repos.TVRepository
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
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
                .mapNotNull {
                    if (it is TVShow) {
                        try {
                            tvMapper.map(it)
                        } catch (e: Exception) {
                            log { "deadpool - TVSHOW failed to map $it" }
                            null
                        }
                    } else {
                        try {
                            movieMapper.map(it as Movie)
                        } catch (e: Exception) {
                            log { "deadpool - MOIVE failed to map $it" }
                            null
                        }
                    }
                }.take(30)
        }
    }
}