package com.sunrisekcdeveloper.medialion.oldArch.repos

import com.sunrisekcdeveloper.medialion.oldArch.clients.TMDBClient
import com.sunrisekcdeveloper.medialion.oldArch.clients.models.MediaResponse
import com.sunrisekcdeveloper.medialion.oldArch.clients.suspendRunReThrowable
import com.sunrisekcdeveloper.medialion.oldArch.data.DispatcherProvider
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.ID
import com.sunrisekcdeveloper.medialion.oldArch.mappers.ListMapper
import com.sunrisekcdeveloper.medialion.oldArch.mappers.Mapper
import database.MovieCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

interface MovieRepository {
    suspend fun movieDetails(id: ID): Result<Movie>
    fun recommendationsForMovie(id: ID): Flow<Movie>
    fun popularMovies(): Flow<Movie>
    fun search(query: String): Flow<Movie>
    fun moviesRelatedTo(id: Int): Flow<Movie>
    fun withGenre(id: ID): Flow<Movie>

    class Default(
        database: MediaLionDatabase,
        private val client: TMDBClient,
        private val dispatcherProvider: DispatcherProvider,
        private val responseToDomain: Mapper<MediaResponse, Movie>,
        private val domainToCache: Mapper<Movie, MovieCache>,
        private val cacheToDomain: Mapper<MovieCache, Movie>,
        private val responseToDomainListMapper: ListMapper<MediaResponse, Movie>,
    ) : MovieRepository {

        private val movieQueries = database.tbl_movieQueries

        override fun withGenre(id: ID): Flow<Movie> = flow<Movie> {
            var page = 1
            var totalPages = Int.MAX_VALUE
            do {
                client.discoverMovie(id.value, page++)
                    .onSuccess {
                        totalPages = it.totalResults
                        val res = it.results.mapNotNull {
                            suspendRunReThrowable("Unable to map response to domain model") {
                                responseToDomain.map(it)
                            }.getOrNull()
                        }
                        emitAll(res.asFlow())
                    }
                    .onFailure { throw Exception("Unable to fetch Movies with genre id [id=$id, page=$page, totalPages=$totalPages]", it.cause) }
            } while (page <= totalPages)
        }
            .onEach { movieQueries.insert(domainToCache.map(it)) }
            .flowOn(dispatcherProvider.io)

        override suspend fun movieDetails(id: ID): Result<Movie> {
//            return withContext(dispatcherProvider.io) {
//                return@withContext suspendRunReThrowable("Unable to retrieve TV show details [id=$id]") {
//                    var cacheMovie = movieQueries.findMovie(id.value).executeAsOneOrNull()
//
//                    if (cacheMovie == null) {
//                        client.movieDetails(id.value)
//                            .onSuccess {
//                                val movie = responseToDomain.map(it)
//                                domainToCache.map(movie).apply {
//                                    movieQueries.insert(this)
//                                    cacheMovie = this
//                                }
//                            }
//                            .onFailure { throw Exception("Unable to fetch movie details from API [id=$id]", it.cause) }
//                    }
//                    cacheToDomain.map(cacheMovie!!)
//                }
//            }
            TODO()
        }

        override fun recommendationsForMovie(id: ID): Flow<Movie> = flow {
            var page = 1
            var totalPages = Int.MAX_VALUE
            do {
                client.recommendationsForMovie(id.value, page++)
                    .onSuccess {
                        totalPages = it.totalPages
                        val res = it.results.mapNotNull {
                            suspendRunReThrowable("Unable to map response to domain model") {
                                responseToDomain.map(it)
                            }.getOrNull()
                        }
                        emitAll(res.asFlow())
                    }
                    .onFailure { throw Exception("Unable to fetch movie recommendations [id=$id, page=$page, totalPages=$totalPages]", it.cause) }
            } while (page <= totalPages)
        }
        .onEach { movieQueries.insert(domainToCache.map(it)) }
        .flowOn(dispatcherProvider.io)

        override fun popularMovies(): Flow<Movie> = flow {
            var page = 1
            var totalPages = Int.MAX_VALUE
            do {
                client.popularMovies(page++)
                    .onSuccess {
                        totalPages = it.totalPages
                        val res = it.results.mapNotNull {
                            suspendRunReThrowable("Unable to map response to domain model") {
                                responseToDomain.map(it)
                            }.getOrNull()
                        }
                        emitAll(res.asFlow())
                    }
                    .onFailure { throw Exception("Unable to fetch popular movies [page=$page, totalPages=$totalPages]", it.cause) }
            } while (page <= totalPages)
        }
        .flowOn(dispatcherProvider.io)

        override fun search(query: String): Flow<Movie> = flow {
            var page = 1
            var totalPages = Int.MAX_VALUE
            do {
                client.searchMovies(query, page++)
                    .onSuccess {
                        totalPages = it.totalPages
                        println("pedro movie search page - ${it.page}")
                        val res = it.results.mapNotNull {
                            suspendRunReThrowable("Unable to map response to domain model") {
                                responseToDomain.map(it)
                            }.getOrNull()
                        }
                        println("pedro movie results size ${res.size}")
                        emitAll(res.asFlow())
                    }
                    .onFailure { throw Exception("Unable to fetch movies [query=$query, page=$page, totalPages=$totalPages]", it.cause) }
            } while (page <= totalPages)
        }
        .onEach { movieQueries.insert(domainToCache.map(it)) }
        .flowOn(dispatcherProvider.io)

        override fun moviesRelatedTo(id: Int): Flow<Movie> = flow {
            var page = 1
            var totalPages = Int.MAX_VALUE
            do {
                client.similarForMovie(id, page++)
                    .onSuccess {
                        totalPages = it.totalPages
                        val res = it.results.mapNotNull {
                            suspendRunReThrowable("Unable to map response to domain model") {
                                responseToDomain.map(it)
                            }.getOrNull()
                        }
                        emitAll(res.asFlow())
                    }
                    .onFailure { throw Exception("Unable to fetch related movies [id=$id, page=$page, totalPages=$totalPages]", it.cause) }
            } while (page <= totalPages)
        }
        .onEach { movieQueries.insert(domainToCache.map(it)) }
        .flowOn(dispatcherProvider.io)
    }
}