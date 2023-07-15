package com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirements
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

interface SingleMediaRemoteDataSource {
    fun mediaFlow(requirements: MediaRequirements): Flow<SingleMediaNetworkDto>

    class D(
        private val mediaApiClient: MediaRemoteClient,
    ) : SingleMediaRemoteDataSource {
        override fun mediaFlow(requirements: MediaRequirements): Flow<SingleMediaNetworkDto> = flow {
            val mediaFlow = if (requirements.withText.trim().isNotEmpty()) {
                mediaApiClient.search(requirements)
            } else {
                mediaApiClient.discover(requirements)
            }

            emitAll(mediaFlow)
        }
    }

    class Fake : SingleMediaRemoteDataSource {
        var forceFailure = false

        private var media: Flow<SingleMediaNetworkDto> = flow {
            var counter = 0
            while (true) {
                val asString = "${++counter}"
                if (counter % 2 == 0)
                    emit(SingleMediaNetworkDto.TVShow(asString, asString, asString))
                else
                    emit(SingleMediaNetworkDto.MovieNetworkDto(asString, asString, asString))
            }
        }

        override fun mediaFlow(requirements: MediaRequirements): Flow<SingleMediaNetworkDto> {
            if (forceFailure) throw Exception("Forced Failure")
            return media
        }
    }
}