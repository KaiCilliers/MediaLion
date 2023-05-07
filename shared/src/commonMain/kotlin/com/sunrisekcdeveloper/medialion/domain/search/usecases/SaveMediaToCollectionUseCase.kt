package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository

interface SaveMediaToCollectionUseCase {
    suspend operator fun invoke(collectionName: Title, mediaId: ID, mediaType: MediaType)
    class Default(
        private val collectionRepo: CollectionRepository,
        private val movieDetails: MovieDetailsUseCase,
        private val tvDetails: TVDetailsUseCase,
    ) : SaveMediaToCollectionUseCase {
        override suspend fun invoke(collectionName: Title, mediaId: ID, mediaType: MediaType) {
            when(mediaType) {
                MediaType.MOVIE -> {
                    movieDetails(mediaId)
                        .onSuccess { collectionRepo.addMediaToCollection(collectionName.value, it) }
                }
                MediaType.TV -> {
                    tvDetails(mediaId)
                        .onSuccess {
                            collectionRepo.addMediaToCollection(collectionName.value, it)
                        }
                }
            }
        }
    }
}