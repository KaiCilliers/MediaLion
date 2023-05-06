package com.example.medialion.domain.search

import com.example.medialion.domain.MediaType
import com.example.medialion.domain.entities.Collection
import com.example.medialion.domain.search.usecases.DocumentariesRelatedToUseCase
import com.example.medialion.domain.search.usecases.MovieDetailsUseCase
import com.example.medialion.domain.search.usecases.MoviesRelatedToUseCase
import com.example.medialion.domain.search.usecases.SuggestedMediaUseCase
import com.example.medialion.domain.search.usecases.TVDetailsUseCase
import com.example.medialion.domain.search.usecases.TVRelatedToUseCase
import com.example.medialion.domain.search.usecases.TopMediaResultsUseCase
import com.example.medialion.domain.value.ID
import com.example.medialion.domain.value.Title
import com.example.medialion.repos.CollectionRepository
import kotlinx.coroutines.flow.Flow

interface SearchComponent {

    val movieDetails: MovieDetailsUseCase
    val tvDetails: TVDetailsUseCase
    val relatedDocumentariesUseCase: DocumentariesRelatedToUseCase
    val relatedMoviesUseCase: MoviesRelatedToUseCase
    val suggestedMediaUseCase: SuggestedMediaUseCase
    val topMediaResultsUseCase: TopMediaResultsUseCase
    val tvRelatedToUseCase: TVRelatedToUseCase

    class Default(
        override val movieDetails: MovieDetailsUseCase,
        override val tvDetails: TVDetailsUseCase,
        override val relatedDocumentariesUseCase: DocumentariesRelatedToUseCase,
        override val relatedMoviesUseCase: MoviesRelatedToUseCase,
        override val suggestedMediaUseCase: SuggestedMediaUseCase,
        override val topMediaResultsUseCase: TopMediaResultsUseCase,
        override val tvRelatedToUseCase: TVRelatedToUseCase,
    ) : SearchComponent
}

interface CollectionComponent {

    val saveMediaToCollection: SaveMediaToCollectionUseCase
    val removeMediaFromCollection: RemoveMediaFromCollectionUseCase
    val createCollection: CreateCollectionUseCase
    val fetchAllCollections: FetchAllCollectionsUseCase
    val fetchCollection: FetchCollectionUseCase

    class Default(
        override val saveMediaToCollection: SaveMediaToCollectionUseCase,
        override val removeMediaFromCollection: RemoveMediaFromCollectionUseCase,
        override val createCollection: CreateCollectionUseCase,
        override val fetchAllCollections: FetchAllCollectionsUseCase,
        override val fetchCollection: FetchCollectionUseCase,
    ) : CollectionComponent

}

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

interface RemoveMediaFromCollectionUseCase {
    suspend operator fun invoke(collectionName: Title, mediaId: ID, mediaType: MediaType)
    class Default(
        private val collectionRepo: CollectionRepository,
    ) : RemoveMediaFromCollectionUseCase {
        override suspend fun invoke(collectionName: Title, mediaId: ID, mediaType: MediaType) {
            collectionRepo.removeMediaFromCollection(collectionName.value, mediaId, mediaType)
        }
    }
}

interface CreateCollectionUseCase {
    suspend operator fun invoke(collectionName: Title)
    class Default(
        private val collectionRepo: CollectionRepository,
    ) : CreateCollectionUseCase {
        override suspend fun invoke(collectionName: Title) {
            collectionRepo.insertCollection(collectionName.value)
        }
    }
}

interface FetchAllCollectionsUseCase {
    operator fun invoke(): Flow<List<Collection>>
    class Default(
        private val collectionRepo: CollectionRepository,
    ) : FetchAllCollectionsUseCase {
        override fun invoke(): Flow<List<Collection>> {
            return collectionRepo.allCollections()
        }
    }
}

interface FetchCollectionUseCase {
    operator fun invoke(collectionName: Title): Flow<Collection>
    class Default(
        private val collectionRepo: CollectionRepository,
    ) : FetchCollectionUseCase {
        override fun invoke(collectionName: Title): Flow<Collection> {
            return collectionRepo.getCollection(collectionName.value)
        }
    }
}