package com.sunrisekcdeveloper.medialion.components.discovery.domain.repo

import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.runCatching
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirements
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.MediaWithTitle
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.SingleMediaItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.koin.ext.getFullName

interface TitledMediaRepository {
    suspend fun withRequirement(mediaReq: MediaRequirements): MediaWithTitle

    class D(
        private val singleMediaItemRepository: SingleMediaItemRepository,
    ) : TitledMediaRepository {
        override suspend fun withRequirement(mediaReq: MediaRequirements): MediaWithTitle = runCatching {
            val media = singleMediaItemRepository.media(mediaReq)
            MediaWithTitle.Def(mediaReq.withTitle, media)
        }.getOrElse { throw Exception("Failed to create ${MediaWithTitle.Def::class.getFullName()}", it) }
    }

    class Fake : TitledMediaRepository {
        var forceFailure = false

        private var media: Flow<SingleMediaItem> = flow {
            var counter = 0
            while (true) {
                if (counter % 5 == 0)
                    emit(SingleMediaItem.TVShow("Item #${++counter}"))
                else emit(SingleMediaItem.Movie("Item #${++counter}"))
            }
        }

        fun providePoolOfMedia(media: List<SingleMediaItem>) {
            this.media = media.asFlow()
        }

        override suspend fun withRequirement(mediaReq: MediaRequirements): MediaWithTitle {
            if (forceFailure) throw Exception("Forced a Failure")
            return MediaWithTitle.Def(
                title = mediaReq.withTitle,
                content = media
                    .filterNot { mediaReq.withoutMedia.contains(it.identifier()) }
                    .take(mediaReq.amountOfMedia)
                    .toList()
            )
        }
    }
}

