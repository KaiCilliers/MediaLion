package com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia

import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.oldArch.mappers.Mapper
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.components.shared.utils.ForcedException
import database.MovieCache
import database.TVShowCache

interface SingleMediaLocalDataSource  {
    suspend fun upsert(media: SingleMediaItem)
    suspend fun find(id: ID): List<SingleMediaItem>

    class D(
        db: MediaLionDatabase,
        private val domainMovieMapper: Mapper<SingleMediaItem, MovieCache>,
        private val domainTVShowMapper: Mapper<SingleMediaItem, TVShowCache>,
        private val cacheMovieMapper: Mapper<MovieCache, SingleMediaItem>,
        private val cacheTVShowMapper: Mapper<TVShowCache, SingleMediaItem>,
    ) : SingleMediaLocalDataSource {

        private val movieDao = db.tbl_movieQueries
        private val tvShowDao = db.tbl_tvshowQueries

        override suspend fun upsert(media: SingleMediaItem) {
            when(media) {
                is SingleMediaItem.Movie -> movieDao.insert(domainMovieMapper.map(media))
                is SingleMediaItem.TVShow -> tvShowDao.insert(domainTVShowMapper.map(media))
            }
        }

        override suspend fun find(id: ID): List<SingleMediaItem> {
            val searchResult: MutableList<SingleMediaItem> = mutableListOf()

            movieDao
                .findMovie(id.uniqueIdentifier())
                .executeAsList()
                .firstOrNull { searchResult.add(cacheMovieMapper.map(it)) }

            if (searchResult.isEmpty()) {
                tvShowDao
                    .findTVShow(id.uniqueIdentifier())
                    .executeAsList()
                    .firstOrNull { searchResult.add(cacheTVShowMapper.map(it)) }
            }

            return searchResult
        }
    }

    class Fake : SingleMediaLocalDataSource {

        var forceFailure = false

        private val mediaCache = mutableSetOf<SingleMediaItem>()

        override suspend fun upsert(media: SingleMediaItem) {
            if (forceFailure) throw ForcedException()
            mediaCache.add(media)
        }

        override suspend fun find(id: ID): List<SingleMediaItem> {
            if (forceFailure) throw ForcedException()
            val results = mutableListOf<SingleMediaItem>()
            mediaCache
                .find { it.identifier() == id }
                ?.also { results.add(it) }

            return results
        }
    }
}