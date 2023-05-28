package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import com.sunrisekcdeveloper.medialion.repos.MovieRepository
import com.sunrisekcdeveloper.medialion.repos.TVRepository
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

interface FetchDiscoveryContent {
    /*
        0 --> All
        1 --> Movie
        2 --> TV
     */
    suspend operator fun invoke(mediaToShow: Int, genreId: ID? = null): List<TitledMedia>
    class Default(
        private val tvRepository: TVRepository,
        private val movieRepository: MovieRepository,
        private val mapperTv: ListMapper<TVShow, MediaItemUI>,
        private val mapperMovie: ListMapper<Movie, MediaItemUI>,
    ) : FetchDiscoveryContent {

        private val tvGenres: List<Pair<ID, String>> = listOf(
            ID(10759) to "Action & Adventure",
            ID(16) to "Animation",
            ID(99) to "Documentary",
            ID(18) to "Drama",
            ID(10751) to "Family",
            ID(10762) to "Kids",
            ID(9648) to "Mystery",
            ID(10764) to "Reality",
            ID(10765) to "Sci-Fi & Fantasy",
            ID(37) to "Western",
        )

        private val movieGenres: List<Pair<ID, String>> = listOf(
            ID(28) to "Action",
            ID(12) to "Adventure",
            ID(16) to "Animation",
            ID(35) to "Comedy",
            ID(80) to "Crime",
            ID(99) to "Documentary",
            ID(18) to "Drama",
            ID(10751) to "Family",
            ID(14) to "Fantasy",
            ID(36) to "History",
            ID(27) to "Horror",
            ID(10402) to "Music",
            ID(9648) to "Mystery",
            ID(10749) to "Romance",
            ID(878) to "Science Fiction",
            ID(10770) to "TV Movie",
            ID(53) to "Thriller",
            ID(10752) to "War",
            ID(37) to "Western",
        )

        private suspend fun fetchMovieContent(): List<TitledMedia> {
            val content = mutableListOf<TitledMedia>()
            movieGenres.shuffled()
                .take(6)
                .forEach { (genreId, title) ->
                    val movies = movieRepository
                        .withGenre(genreId)
                        .onEach { log { "deadpool - ${it.id}" } }
                        .take(20)
                        .toList()
                    TitledMedia(
                        title = title,
                        content = mapperMovie.map(movies)
                    ).also {
                        content.add(it)
                    }
                }
            return content
        }

        private suspend fun fetchTvContent(): List<TitledMedia> {
            val content = mutableListOf<TitledMedia>()
            tvGenres.shuffled()
                .take(6)
                .forEach { (genreId, title) ->
                    val tvShows = tvRepository
                        .withGenre(genreId)
                        .onEach { log { "deadpool - ${it.id}" } }
                        .take(20)
                        .toList()
                    TitledMedia(
                        title = title,
                        content = mapperTv.map(tvShows)
                    ).also {
                        content.add(it)
                    }
                }
            return content
        }

        override suspend fun invoke(mediaToShow: Int, genreId: ID?): List<TitledMedia> {
            val content = mutableListOf<TitledMedia>()
            if (genreId == null) {
                when (mediaToShow) {
                    1 -> content.addAll(fetchMovieContent())
                    2 -> content.addAll(fetchTvContent())
                    else -> {
                        content.addAll(fetchMovieContent())
                        content.addAll(fetchTvContent())
                    }
                }
            } else {
                content.add(fetchGenreMedia(mediaToShow, genreId))
            }
            return content.shuffled().take(6)
        }

        private suspend fun fetchGenreMedia(mediaType: Int, genreId: ID): TitledMedia {
            return when(mediaType) {
                1 -> {
                    val media = movieRepository
                        .withGenre(genreId)
                        .take(40)
                        .toList()
                    TitledMedia(
                        title = movieGenres.find { it.first == genreId }?.second ?: "Genre",
                        content = mapperMovie.map(media)
                    )
                }
                else -> {
                    val media = tvRepository
                        .withGenre(genreId)
                        .take(40)
                        .toList()
                    TitledMedia(
                        title = tvGenres.find { it.first == genreId }?.second ?: "Genre",
                        content = mapperTv.map(media)
                    )
                }
            }
        }
    }
}

