package com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirements
import kotlinx.coroutines.flow.Flow

interface MediaRemoteClient {
    fun search(requirements: MediaRequirements): Flow<SingleMediaApiDto>
    fun discover(requirements: MediaRequirements): Flow<SingleMediaApiDto>
}