package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.repo

import com.sunrisekcdeveloper.medialion.mappers.Mapper
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaTypeNew
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory.MediaCategoryApiDto
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory.MediaCategoryEntityDto
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory.MediaCategoryLocalDataSource
import com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory.MediaCategoryRemoteDataSource

interface MediaCategoryRepository {
    suspend fun all(): List<MediaCategory>
    suspend fun getRandomOrAll(amount: Int, mediaType: MediaTypeNew = MediaTypeNew.All): List<MediaCategory>

    class D(
        private val localDataSource: MediaCategoryLocalDataSource,
        private val remoteDataSource: MediaCategoryRemoteDataSource,
        private val apiMapper: Mapper<MediaCategoryApiDto, MediaCategory>,
        private val entityMapper: Mapper<MediaCategoryEntityDto, MediaCategory>,
        private val domainMapper: Mapper<MediaCategory, MediaCategoryEntityDto>,
    ) : MediaCategoryRepository {

        override suspend fun all(): List<MediaCategory> = runCatching {
            var cachedCategories: List<MediaCategoryEntityDto> = localDataSource.all()
            if (cachedCategories.isEmpty()) {
                val remoteCategories: List<MediaCategoryApiDto> = remoteDataSource.all()
                remoteCategories
                    .map { apiDto -> apiMapper.map(apiDto)}
                    .map { domainModel -> domainMapper.map(domainModel) }
                    .forEach { entityDto -> localDataSource.add(entityDto)  }
                cachedCategories = localDataSource.all()
            }
            return cachedCategories.map { entityDto -> entityMapper.map(entityDto) }
        }.getOrElse { throw Exception("Failed to retrieve all media categories", it) }

        override suspend fun getRandomOrAll(amount: Int, mediaType: MediaTypeNew): List<MediaCategory> = runCatching {
            all()
                .filter { it.appliesToType(mediaType) }
                .take(amount)
        }.getOrElse { throw Exception("Failed to retrieve a random amount of media categories [amount=$amount, mediaType=$mediaType]", it) }
    }

    class Fake : MediaCategoryRepository {

        var forceFailure = false

        private val mediaCategories = listOf(
            MediaCategory.D("1"),
            MediaCategory.D("2", appliesToType = MediaTypeNew.Movie),
            MediaCategory.D("3"),
            MediaCategory.D("4", appliesToType = MediaTypeNew.TVShow),
            MediaCategory.D("5"),
            MediaCategory.D("6", appliesToType = MediaTypeNew.TVShow),
            MediaCategory.D("7"),
            MediaCategory.D("8"),
            MediaCategory.D("9"),
            MediaCategory.D("10"),
        )

        override suspend fun all(): List<MediaCategory> {
            if (forceFailure) throw Exception("Forced a Failure")
            return mediaCategories
        }

        override suspend fun getRandomOrAll(amount: Int, mediaType: MediaTypeNew): List<MediaCategory> {
            if (forceFailure) throw Exception("Forced a Failure")
            return mediaCategories
                .shuffled()
                .filter { mediaCategory -> mediaCategory.appliesToType(mediaType) }
                .take(amount)
        }
    }
}