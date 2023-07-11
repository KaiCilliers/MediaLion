package com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.DatabaseAccessError
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.LocalCacheError
import com.sunrisekcdeveloper.medialion.newarch.components.shared.domain.models.SingleMediaItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

interface SingleMediaItemRepository {
    fun mediaFlow(ofType: String): Result<Flow<SingleMediaItem>, LocalCacheError>
    class Fake : SingleMediaItemRepository {

        var forceFailure = false

        private var media: Flow<SingleMediaItem> = flow {
            var counter = 0
            while (true) {
                emit(SingleMediaItem.Def("Item #${++counter}"))
            }
        }

        override fun mediaFlow(ofType: String): Result<Flow<SingleMediaItem>, LocalCacheError> {
            return if (!forceFailure) {
                Ok(media)
            } else {
                Err(DatabaseAccessError)
            }
        }

        fun providePoolOfMedia(media: List<SingleMediaItem>) {
            this.media = media.asFlow()
        }
    }
    class Smart(
        private val origin: SingleMediaItemRepository
    ) {
        fun suggestedMediaFlow(): Result<Flow<SingleMediaItem>, LocalCacheError> {
            return origin.mediaFlow("Suggested Media")
        }
    }
}