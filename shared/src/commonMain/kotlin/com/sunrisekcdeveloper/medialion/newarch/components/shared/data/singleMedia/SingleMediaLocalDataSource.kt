package com.sunrisekcdeveloper.medialion.newarch.components.shared.data.singleMedia

import com.sunrisekcdeveloper.medialion.newarch.components.discovery.domain.models.MediaRequirements
import kotlinx.coroutines.flow.Flow

interface SingleMediaLocalDataSource  {
    fun mediaFlow(requirements: MediaRequirements): Flow<SingleMediaEntityDto>
}