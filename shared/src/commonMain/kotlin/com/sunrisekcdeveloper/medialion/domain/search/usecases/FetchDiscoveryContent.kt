package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.TitledMedia
import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import com.sunrisekcdeveloper.medialion.repos.MovieRepository
import com.sunrisekcdeveloper.medialion.repos.TVRepository
import io.github.aakira.napier.log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

interface FetchDiscoveryContent {
    /*
        0 --> All
        1 --> Movie
        2 --> TV
     */
    suspend operator fun invoke(mediaToShow: MediaType?, genreId: ID? = null): List<TitledMedia>
    class Default(
        private val tvRepository: TVRepository,
        private val movieRepository: MovieRepository,
        private val mapperTv: ListMapper<TVShow, MediaItemUI>,
        private val mapperMovie: ListMapper<Movie, MediaItemUI>,
    ) : FetchDiscoveryContent {

        private val inMemoryCache = DiscoveryContentCache()

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
            val cache = inMemoryCache.get(MediaType.MOVIE)
            if (cache == null) {
                movieGenres.shuffled()
                    .take(6)
                    .forEach { (genreId, title) ->
                        val movies = movieRepository
                            .withGenre(genreId)
                            .onEach { log { "deadpool - ${it.id}" } }
                            .take(22)
                            .toList()
                        TitledMedia(
                            title = title,
                            content = mapperMovie.map(movies)
                        ).also {
                            content.add(it)
                        }
                    }
            } else {
                content.addAll(cache)
            }
            inMemoryCache.set(MediaType.MOVIE, content)
            return content
        }

        private suspend fun fetchTvContent(): List<TitledMedia> {
            val content = mutableListOf<TitledMedia>()
            val cache = inMemoryCache.get(MediaType.TV)
            if (cache == null) {
                tvGenres.shuffled()
                    .take(6)
                    .forEach { (genreId, title) ->
                        val tvShows = tvRepository
                            .withGenre(genreId)
                            .onEach { log { "deadpool - ${it.id}" } }
                            .take(22)
                            .toList()
                        TitledMedia(
                            title = title,
                            content = mapperTv.map(tvShows)
                        ).also {
                            content.add(it)
                        }
                    }
            } else {
                content.addAll(cache)
            }
            inMemoryCache.set(MediaType.TV, content)
            return content
        }

        override suspend fun invoke(mediaToShow: MediaType?, genreId: ID?): List<TitledMedia> {
            val content = mutableListOf<TitledMedia>()
            val asyncOperations = mutableListOf<Deferred<List<TitledMedia>>>()
            coroutineScope {
                if (genreId == null) {
                    when (mediaToShow) {
                        MediaType.MOVIE -> async { fetchMovieContent() }.also { asyncOperations.add(it) }
                        MediaType.TV -> async { fetchTvContent() }.also { asyncOperations.add(it) }
                        null -> {
                            async { fetchMovieContent() }.also { asyncOperations.add(it) }
                            async { fetchTvContent() }.also { asyncOperations.add(it) }
                        }
                    }
                } else {
                    async { fetchGenreMedia(mediaToShow!!, genreId) }.also { asyncOperations.add(it) }
                }
            }
            asyncOperations
                .awaitAll()
                .flatten()
                .shuffled()
                .take(6)
                .forEach {
                    content.add(it)
                }
            return content
        }

        private suspend fun fetchGenreMedia(mediaType: MediaType, genreId: ID): List<TitledMedia> {
            return inMemoryCache.get(genreId, mediaType)
                ?: when (mediaType) {
                    MediaType.MOVIE -> {
                        val media = movieRepository
                            .withGenre(genreId)
                            .take(42)
                            .toList()
                        val titledMedia = listOf(
                            TitledMedia(
                                title = movieGenres.find { it.first == genreId }?.second ?: "Genre",
                                content = mapperMovie.map(media)
                            )
                        )
                        inMemoryCache.set(genreId, mediaType, titledMedia)
                        titledMedia
                    }

                    MediaType.TV -> {
                        val media = tvRepository
                            .withGenre(genreId)
                            .take(40)
                            .toList()
                        val titledMedia = listOf(
                            TitledMedia(
                                title = tvGenres.find { it.first == genreId }?.second ?: "Genre",
                                content = mapperTv.map(media)
                            )
                        )
                        inMemoryCache.set(genreId, mediaType, titledMedia)
                        titledMedia
                    }
                }
        }
    }
}

private class DiscoveryContentCache {

    private val mediaTypeCache: MutableMap<MediaType, List<TitledMedia>> = mutableMapOf()
    private val categoryMediaCache: MutableMap<Pair<ID, MediaType>, List<TitledMedia>> = mutableMapOf()

    fun get(mediaType: MediaType): List<TitledMedia>? {
        return mediaTypeCache[mediaType]
    }

    fun get(genreId: ID, mediaType: MediaType): List<TitledMedia>? {
        return categoryMediaCache[(genreId to mediaType)]
    }

    fun set(mediaType: MediaType, media: List<TitledMedia>) {
        mediaTypeCache[mediaType] = media
    }

    fun set(genreId: ID, mediaType: MediaType, media: List<TitledMedia>) {
        categoryMediaCache[(genreId to mediaType)] = media
    }
}
