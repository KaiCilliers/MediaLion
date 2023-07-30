package com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryEntityDto
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.utils.mappers.Mapper
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.components.shared.utils.ForcedException
import database.CategoryCache
import database.MovieCache
import database.TVShowCache

interface SingleMediaLocalDataSource {
    suspend fun upsert(media: SingleMediaItem)
    suspend fun find(id: ID): List<SingleMediaItem>

    class D(
        db: MediaLionDatabase,
        private val domainMovieMapper: Mapper<SingleMediaItem.Movie, MovieCache>,
        private val domainTVShowMapper: Mapper<SingleMediaItem.TVShow, TVShowCache>,
        private val cacheMovieMapper: Mapper<MovieCache, SingleMediaItem.Movie>,
        private val cacheTVShowMapper: Mapper<TVShowCache, SingleMediaItem.TVShow>,
        private val mediaCategoryMapper: Mapper<CategoryCache, MediaCategoryEntityDto>,
        private val mediaCategoryDomainMapper: Mapper<MediaCategoryEntityDto, MediaCategory>,
    ) : SingleMediaLocalDataSource {

        private val movieDao = db.tbl_movieQueries
        private val tvShowDao = db.tbl_tvshowQueries
        private val categoriesDao = db.tbl_categoriesQueries

        override suspend fun upsert(media: SingleMediaItem) {
            when (media) {
                is SingleMediaItem.Movie -> movieDao.insert(domainMovieMapper.map(media))
                is SingleMediaItem.TVShow -> tvShowDao.insert(domainTVShowMapper.map(media))
            }
        }

        override suspend fun find(id: ID): List<SingleMediaItem> {
            val searchResult: MutableList<SingleMediaItem> = mutableListOf()

            movieDao
                .findMovie(id.uniqueIdentifier())
                .executeAsList()
                .firstOrNull { movieCache ->
                    var movie = cacheMovieMapper.map(movieCache)
                    movie = movie.copy(categories = categoriesFromIds(movieCache.genre_ids.map { it.toString() }))
                    searchResult.add(movie)
                }

            if (searchResult.isEmpty()) {
                tvShowDao
                    .findTVShow(id.uniqueIdentifier())
                    .executeAsList()
                    .firstOrNull { tvCache ->
                        var tvShow = cacheTVShowMapper.map(tvCache)
                        tvShow = tvShow.copy(categories = categoriesFromIds(tvCache.genre_ids.map { it.toString() }))
                        searchResult.add(tvShow)
                    }
            }

            return searchResult
        }

        private fun categoriesFromIds(categoryIds: List<String>): List<MediaCategory> {
            return categoriesDao
                .all()
                .executeAsList()
                .map { mediaCategoryMapper.map(it) }
                .map { mediaCategoryDomainMapper.map(it) }
                .filter { categoryIds.contains(it.identifier().uniqueIdentifier()) }
        }
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