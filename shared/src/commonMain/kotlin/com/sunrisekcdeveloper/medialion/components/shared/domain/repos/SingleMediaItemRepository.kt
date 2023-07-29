package com.sunrisekcdeveloper.medialion.components.shared.domain.repos

import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirements
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.MediaCategoryRepository
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.SingleMediaApiDto
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.SingleMediaLocalDataSource
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.SingleMediaRemoteDataSource
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.utils.mappers.Mapper
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

interface SingleMediaItemRepository {
    suspend fun media(mediaReq: MediaRequirements): List<SingleMediaItem>

    class D(
        private val remoteDataSource: SingleMediaRemoteDataSource,
        private val localDataSource: SingleMediaLocalDataSource,
        private val dtoMapper: Mapper<SingleMediaApiDto, SingleMediaItem>,
        private val categoryRepository: MediaCategoryRepository,
    ) : SingleMediaItemRepository {

        override suspend fun media(mediaReq: MediaRequirements): List<SingleMediaItem> = runCatching {
            val mediaCategories = categoryRepository.all()
            val remoteFlow: Flow<SingleMediaApiDto> = remoteDataSource.mediaFlow(mediaReq)
            remoteFlow
                .mapNotNull { mediaApiDto ->
                    try {
                        var singleMediaItem = dtoMapper.map(mediaApiDto)
                        val categoriesFromGenres = mediaApiDto.genreIds.mapNotNull { genreId -> mediaCategories.find { it.identifier().uniqueIdentifier() == genreId.toString() } }
                        singleMediaItem = when(singleMediaItem) {
                            is SingleMediaItem.Movie -> singleMediaItem.copy(categories = categoriesFromGenres)
                            is SingleMediaItem.TVShow -> singleMediaItem.copy(categories = categoriesFromGenres)
                        }
                        singleMediaItem
                    } catch (e: Exception) {
                        Napier.w(e) { "Failed to map remote media model to domain model [$mediaApiDto]" }
                        null
                    }
                }
                .take(mediaReq.amountOfMedia)
                .onEach { singleMediaItem -> saveItemLocally(singleMediaItem) }
                .toList()
        }.getOrElse { throw Exception("Failed to fetch media from remote datasource with requirements $mediaReq", it) }

        private suspend fun saveItemLocally(item: SingleMediaItem) = runCatching {
            localDataSource.upsert(item)
        }.onFailure {
            // ignore failure, just log
            Exception("Failed in saving media item to local storage --> ${item.identifier()}", it).printStackTrace()
        }

    }

    class Fake : SingleMediaItemRepository {

        var forceFailure = false

        private var media: Flow<SingleMediaItem> = flow {
            var counter = 0
            while (true) {
                if (counter % 5 == 0)
                    emit(SingleMediaItem.Movie("Item #${++counter}"))
                else emit(SingleMediaItem.TVShow("Item #${++counter}"))
            }
        }

        override suspend fun media(mediaReq: MediaRequirements): List<SingleMediaItem> {
            if (forceFailure) throw Exception("Forced a Failure")
            return media
                .take(mediaReq.amountOfMedia)
                .toList()
        }

        fun providePoolOfMedia(media: List<SingleMediaItem>) {
            this.media = media.asFlow()
        }
    }
}