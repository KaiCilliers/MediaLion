package com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory

import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import com.sunrisekcdeveloper.medialion.oldArch.mappers.Mapper
import com.sunrisekcdeveloper.medialion.components.shared.utils.infiniteFlowOf
import database.CategoryCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlin.properties.Delegates

interface MediaCategoryLocalDataSource {
    suspend fun all(): List<MediaCategoryEntityDto>
    suspend fun add(item: MediaCategoryEntityDto)

    class D(
        db: MediaLionDatabase,
        private val cacheMapper: Mapper<CategoryCache, MediaCategoryEntityDto>,
        private val entityMapper: Mapper<MediaCategoryEntityDto, CategoryCache>,
    ) : MediaCategoryLocalDataSource {

        private val categoriesDao = db.tbl_categoriesQueries

        override suspend fun all(): List<MediaCategoryEntityDto> {
            return categoriesDao
                .all()
                .executeAsList()
                .map { item -> cacheMapper.map(item) }
        }

        override suspend fun add(item: MediaCategoryEntityDto) {
            entityMapper.map(item).also { categoriesDao.insert(it) }
        }
    }

    class Fake : MediaCategoryLocalDataSource {

        private val categoriesSource: Flow<MediaCategoryEntityDto> = infiniteFlowOf {
            MediaCategoryEntityDto(it)
        }

        private val categoriesAdded: MutableList<MediaCategoryEntityDto> = mutableListOf(
            MediaCategoryEntityDto(1),
            MediaCategoryEntityDto(1),
            MediaCategoryEntityDto(2),
            MediaCategoryEntityDto(2),
        )

        var clearCache by Delegates.observable(false)  { _, _, clearIt ->
            if (clearIt) {
                categoriesAdded.clear()
            }
        }
        var forceFailure = false

        override suspend fun all(): List<MediaCategoryEntityDto> {
            if (forceFailure) throw Exception("Forced a failure")
            return categoriesAdded + if (!clearCache) categoriesSource.take(14).toList() else listOf()
        }

        override suspend fun add(item: MediaCategoryEntityDto) {
            if (forceFailure) throw Exception("Forced a failure")
            categoriesAdded.add(item)
        }
    }
}