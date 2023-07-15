package com.sunrisekcdeveloper.medialion.newarch.components.shared.data.mediaCategory

import com.sunrisekcdeveloper.medialion.newarch.components.shared.utils.infiniteFlowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

interface MediaCategoryRemoteDataSource {
    suspend fun all(): List<MediaCategoryApiDto>

    class D(
        private val apiClient: CategoryRemoteClient
    ) : MediaCategoryRemoteDataSource {
        override suspend fun all(): List<MediaCategoryApiDto> {
            return apiClient.allCategories()
        }
    }

    class Fake : MediaCategoryRemoteDataSource {

        var forceFailure = false

        private val categoriesSource: Flow<MediaCategoryApiDto> = infiniteFlowOf {
            MediaCategoryApiDto(it.toString(), it.toString())
        }

        override suspend fun all(): List<MediaCategoryApiDto> {
            if (forceFailure) throw Exception("Forced a failure")
            return categoriesSource.take(14).toList()
        }
    }
}