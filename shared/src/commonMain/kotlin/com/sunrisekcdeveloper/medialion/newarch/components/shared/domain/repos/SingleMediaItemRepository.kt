package com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.repos

import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirements
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.SingleMediaItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

interface SingleMediaItemRepository {
    suspend fun flowMatchingRequirements(mediaReq: MediaRequirements): Flow<SingleMediaItem>

    class Fake : SingleMediaItemRepository {

        var forceFailure = false

        private var media: Flow<SingleMediaItem> = flow {
            var counter = 0
            while (true) {
                emit(SingleMediaItem.Def("Item #${++counter}"))
            }
        }

        override suspend fun flowMatchingRequirements(mediaReq: MediaRequirements): Flow<SingleMediaItem> {
            TODO("Not yet implemented")
        }

        fun providePoolOfMedia(media: List<SingleMediaItem>) {
            this.media = media.asFlow()
        }
    }
}