package com.example.medialion.data.datasource

import com.example.medialion.data.clients.TMDBClient
import com.example.medialion.data.models.TVShowListResponse
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.models.TVShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

interface TVRemoteDataSource {
    fun discoverWithGenre(id: Int): Flow<TVShow>
    fun relatedTo(id: Int): Flow<TVShow>
    class Default(
        private val api: TMDBClient,
        private val tvListMapper: ListMapper<TVShowListResponse, TVShow>,
    ) : TVRemoteDataSource {
        override fun discoverWithGenre(id: Int): Flow<TVShow> = flow {
            var page = 1
            do {
                val response = api.discoverTv(id, page++)
                emitAll(tvListMapper.map(response.results).asFlow())
            } while (page <= response.totalPages)
        }

        override fun relatedTo(id: Int): Flow<TVShow> = flow {
            var page = 1
            do {
                val response = api.recommendationsForTv(id, page++)
                emitAll(tvListMapper.map(response.results).asFlow())
            } while (page <= response.totalPages)
        }
    }
}