package com.sunrisekcdeveloper.medialion.newarch.components.shared.data.singleMedia

import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirements
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SingleMediaRemoteDataSource {
    fun mediaFlow(requirements: MediaRequirements): Flow<SingleMediaNetworkDto>

    class Fake : SingleMediaRemoteDataSource {
        var forceFailure = false

        private var media: Flow<SingleMediaNetworkDto> = flow {
            var counter = 0
            while (true) {
                emit(SingleMediaNetworkDto(++counter))
            }
        }

        override fun mediaFlow(requirements: MediaRequirements): Flow<SingleMediaNetworkDto> {
            if (forceFailure) throw Exception("Forced Failure")
            return media
        }
    }
}