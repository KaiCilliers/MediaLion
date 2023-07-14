package com.sunrisekcdeveloper.medialion.newarch.components.shared.data

import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirements
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SingleMediaDataSource<T> {
    fun mediaFlow(requirements: MediaRequirements): Flow<SingleMediaNetworkDTO>
}

interface SingleMediaRemoteDataSource : SingleMediaDataSource<SingleMediaNetworkDTO> {
    class Fake : SingleMediaRemoteDataSource {
        var forceFailure = false

        private var media: Flow<SingleMediaNetworkDTO> = flow {
            var counter = 0
            while (true) {
                emit(SingleMediaNetworkDTO(++counter))
            }
        }

        override fun mediaFlow(requirements: MediaRequirements): Flow<SingleMediaNetworkDTO> {
            if (forceFailure) throw Exception("Forced Failure")
            return media
        }
    }
}
interface SingleMediaLocalDataSource : SingleMediaDataSource<SingleMediaEntityDTO>