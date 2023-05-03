package com.example.medialion.data.repos

import com.example.medialion.data.datasource.TVRemoteDataSource
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.domain.models.MovieDetail
import com.example.medialion.domain.models.TVShow
import com.example.medialion.domain.models.TVShowDetail
import com.example.medialion.local.MovieLocalDataSource
import com.example.medialion.local.TVLocalDataSource
import database.MovieEntity
import database.TvShowEntity
import kotlinx.coroutines.flow.Flow

interface TVRepository {
    suspend fun tvDetails(id: Int): TVShowDetail
    fun withGenre(id: Int): Flow<TVShow>
    fun relatedTo(id: Int): Flow<TVShow>
    fun search(query: String): Flow<TVShow>
    class Default(
        private val tvRemoteDataSource: TVRemoteDataSource,
        private val localDataSource: TVLocalDataSource,
        private val mapper: Mapper<TVShowDetail, TvShowEntity>,
    ) : TVRepository {
        override suspend fun tvDetails(id: Int): TVShowDetail {
            val entity = localDataSource.tvDetails(id)
            if(entity == null) {
                val response = tvRemoteDataSource.tvShowDetails(id)
                localDataSource.addDetailedTV(mapper.map(response))
            }
            val ent = localDataSource.tvDetails(id)
            return ent!!
        }

        override fun withGenre(id: Int): Flow<TVShow> {
            return tvRemoteDataSource.discoverWithGenre(id)
        }

        override fun relatedTo(id: Int): Flow<TVShow> {
            return tvRemoteDataSource.relatedTo(id)
        }

        override fun search(query: String): Flow<TVShow> {
            return tvRemoteDataSource.search(query)
        }
    }
}
