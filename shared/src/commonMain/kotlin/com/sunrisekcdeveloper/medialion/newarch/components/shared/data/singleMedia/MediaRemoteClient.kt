package com.sunrisekcdeveloper.medialion.newarch.components.shared.data.singleMedia

import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirements
import kotlinx.coroutines.flow.Flow

interface MediaRemoteClient {
    fun search(requirements: MediaRequirements): Flow<SingleMediaNetworkDto>
    fun discover(requirements: MediaRequirements): Flow<SingleMediaNetworkDto>
}