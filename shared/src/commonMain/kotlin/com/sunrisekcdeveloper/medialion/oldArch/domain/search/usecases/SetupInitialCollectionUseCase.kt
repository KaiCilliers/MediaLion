package com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases

import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import com.sunrisekcdeveloper.medialion.oldArch.repos.CollectionRepository
import com.sunrisekcdeveloper.medialion.oldArch.repos.MovieRepository
import com.sunrisekcdeveloper.medialion.oldArch.repos.TVRepository
import kotlinx.coroutines.flow.first

interface SetupInitialCollectionUseCase {
    suspend operator fun invoke()
    class Default(
        private val collectionRepo: CollectionRepository,
        private val movieRepository: MovieRepository,
        private val tvRepository: TVRepository,
    ) : SetupInitialCollectionUseCase {
        override suspend fun invoke() {
            val prePackagedCollections = collectionRepo
                .allCollections()
                .first()
                .filter { PRE_PACKAGED_COLLECTIONS.contains(it.name.value) }

            if (prePackagedCollections.size < PRE_PACKAGED_COLLECTIONS.size) {
                PRE_PACKAGED_COLLECTIONS.forEach { collectionName ->
                    collectionRepo.deleteCollection(Title(collectionName))
                    collectionRepo.insertCollection(collectionName)
                }
            }
        }
    }

    companion object {
        val PRE_PACKAGED_COLLECTIONS =
            listOf("Favorites")
    }
}