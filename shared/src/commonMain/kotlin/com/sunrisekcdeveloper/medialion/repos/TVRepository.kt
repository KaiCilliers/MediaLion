package com.sunrisekcdeveloper.medialion.repos

import com.sunrisekcdeveloper.medialion.clients.TMDBClient
import com.sunrisekcdeveloper.medialion.clients.models.MediaResponse
import com.sunrisekcdeveloper.medialion.clients.suspendRunReThrowable
import com.sunrisekcdeveloper.medialion.data.DispatcherProvider
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import com.sunrisekcdeveloper.medialion.mappers.Mapper
import database.TVShowCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

interface TVRepository {
    suspend fun tvDetails(id: ID): Result<TVShow>
    fun withGenre(id: ID): Flow<TVShow>
    fun relatedTo(id: ID): Flow<TVShow>
    fun search(query: String): Flow<TVShow>
    fun popularTV(): Flow<TVShow>
    class Default(
        database: MediaLionDatabase,
        private val client: TMDBClient,
        private val dispatcherProvider: DispatcherProvider,
        private val responseToDomain: Mapper<MediaResponse, TVShow>,
        private val domainToCache: Mapper<TVShow, TVShowCache>,
        private val cacheToDomain: Mapper<TVShowCache, TVShow>,
        private val responseToDomainListMapper: ListMapper<MediaResponse, TVShow>,
    ) : TVRepository {

        private val tvShowQueries = database.tbl_tvshowQueries

        override suspend fun tvDetails(id: ID): Result<TVShow> {
            return withContext(dispatcherProvider.io) {
                return@withContext suspendRunReThrowable("Unable to retrieve TV show details [id=$id]") {
                    var cacheTVShow = tvShowQueries.findTVShow(id.value).executeAsOneOrNull()

                    if (cacheTVShow == null) {
                        client.tvDetails(id.value)
                            .onSuccess {
                                val tvShow = responseToDomain.map(it)
                                domainToCache.map(tvShow).apply {
                                    tvShowQueries.insert(this)
                                    cacheTVShow = this
                                }
                            }
                            .onFailure { throw Exception("Unable to fetch tv details from API [id=$id]", it.cause) }
                    }
                    cacheToDomain.map(cacheTVShow!!)
                }
            }
        }

        override fun withGenre(id: ID): Flow<TVShow> = flow {
            var page = 1
            var totalPages = Int.MAX_VALUE
            do {
                client.discoverTv(id.value, page++)
                    .onSuccess {
                        totalPages = it.totalResults
                        val res = it.results.mapNotNull {
                            suspendRunReThrowable("Unable to map response to domain model") {
                                responseToDomain.map(it)
                            }.getOrNull()
                        }
                        emitAll(res.asFlow())
                    }
                    .onFailure { throw Exception("Unable to fetch TV shows with genre id [id=$id, page=$page, totalPages=$totalPages]", it.cause) }
            } while (page <= totalPages)
        }
            .onEach { tvShowQueries.insert(domainToCache.map(it)) }
            .flowOn(dispatcherProvider.io)

        override fun relatedTo(id: ID): Flow<TVShow> = flow {
            var page = 1
            var totalPages = Int.MAX_VALUE
            do {
                client.recommendationsForTv(id.value, page++)
                    .onSuccess {
                        totalPages = it.totalResults
                        val res = it.results.mapNotNull {
                            suspendRunReThrowable("Unable to map response to domain model") {
                                responseToDomain.map(it)
                            }.getOrNull()
                        }
                        emitAll(res.asFlow())
                    }
                    .onFailure {
                        throw Exception(
                            "Unable to fetch recommendations for TV show [id=$id, page=$page, totalPages=$totalPages]",
                            it.cause
                        )
                    }
            } while (page <= totalPages)
        }
            .onEach { tvShowQueries.insert(domainToCache.map(it)) }
            .flowOn(dispatcherProvider.io)

        override fun search(query: String): Flow<TVShow> = flow {
            var page = 1
            var totalPages = Int.MAX_VALUE
            do {
                client.searchTvShows(query, page++)
                    .onSuccess {
                        totalPages = it.totalResults
                        val res = it.results.mapNotNull {
                            suspendRunReThrowable("Unable to map response to domain model") {
                                responseToDomain.map(it)
                            }.also {
                                if (it.isFailure) {
                                    println("deadpool - failed to map $it")
                                    it.exceptionOrNull()?.printStackTrace()
                                }
                            }.getOrNull()
                        }
                        emitAll(res.asFlow())
                    }
                    .onFailure {
                        throw Exception(
                            "Unable to fetch TV shows [query=$query, page=$page, totalPages=$totalPages]",
                            it.cause
                        )
                    }
            } while (page <= totalPages)
        }
            .onEach { tvShowQueries.insert(domainToCache.map(it)) }
            .flowOn(dispatcherProvider.io)

        override fun popularTV(): Flow<TVShow> = flow<TVShow> {
            var page = 1
            var totalPages = Int.MAX_VALUE
            do {
                client.popularTv(page++)
                    .onSuccess {
                        totalPages = it.totalResults
                        val res = it.results.mapNotNull {
                            suspendRunReThrowable("Unable to map response to domain model") {
                                responseToDomain.map(it)
                            }.also {
                                if (it.isFailure) {
                                    println("deadpool - failed to map $it")
                                    it.exceptionOrNull()?.printStackTrace()
                                }
                            }.getOrNull()
                        }
                        emitAll(res.asFlow())
                    }
                    .onFailure {
                        throw Exception(
                            "Unable to fetch popular TV shows [page=$page, totalPages=$totalPages]",
                            it.cause
                        )
                    }
            } while (page <= totalPages)
        }
            .onEach { tvShowQueries.insert(domainToCache.map(it)) }
            .flowOn(dispatcherProvider.io)
    }
}
