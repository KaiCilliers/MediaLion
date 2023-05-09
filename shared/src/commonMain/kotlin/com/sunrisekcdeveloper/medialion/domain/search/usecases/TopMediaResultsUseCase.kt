package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.mappers.Mapper
import com.sunrisekcdeveloper.medialion.repos.MovieRepository
import com.sunrisekcdeveloper.medialion.repos.TVRepository
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
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
//            val tvShowSearchResults = tvRepo.search(searchQuery)
//                .onStart { log { "searching for tv shows matching query $searchQuery" } }
//                .take(10)
            return movieRepo.search(searchQuery)
                .onStart { log { "searching for movies matching query $searchQuery" } }
                .map {
                    log { "ok, mapping a movie ${it.id.value}" }
                    val movie = movieMapper.map(it)
                    log { "ok, done mapping a movie ${movie.id.value}" }
                    movie
                }
                .take(10)

//            return merge(tvShowSearchResults, movieSearchResults)
//                .mapNotNull {
//                    if (it is TVShow) {
//                        try {
//                            tvMapper.map(it)
//                        } catch (e: Exception) {
//                            log { "deadpool - TVSHOW failed to map $it" }
//                            null
//                        }
//                    } else {
//                        try {
//                            movieMapper.map(it as Movie)
//                        } catch (e: Exception) {
//                            log { "deadpool - MOIVE failed to map $it" }
//                            null
//                        }
//                    }
//                }.take(10)
        }
    }
}