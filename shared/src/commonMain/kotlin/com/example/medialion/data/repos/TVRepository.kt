package com.example.medialion.data.repos

import com.example.medialion.data.datasource.TVRemoteDataSource
import com.example.medialion.domain.models.TVShow
import kotlinx.coroutines.flow.Flow

interface TVRepository {
    fun withGenre(id: Int): Flow<TVShow>
    fun relatedTo(id: Int): Flow<TVShow>
    class Default(
        private val tvRemoteDataSource: TVRemoteDataSource
    ) : TVRepository {
        override fun withGenre(id: Int): Flow<TVShow> {
            return tvRemoteDataSource.discoverWithGenre(id)
        }

        override fun relatedTo(id: Int): Flow<TVShow> {
            return tvRemoteDataSource.relatedTo(id)
        }
    }
}
