package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.domain.value.Title
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository
import com.sunrisekcdeveloper.medialion.repos.MovieRepository
import com.sunrisekcdeveloper.medialion.repos.TVRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

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
                    when {
                        collectionName == "Favorites" -> {
                            val media = mutableListOf<MediaItem>()
                            coroutineScope {
                                launch {
                                    movieRepository.popularMovies()
                                        .take(10)
                                        .onEach { media.add(it) }
                                        .collect()
                                }
                                launch {
                                    tvRepository.popularTV()
                                        .take(10)
                                        .onEach { media.add(it) }
                                        .collect()
                                }
                            }
                            media.shuffled().forEach {
                                collectionRepo.addMediaToCollection(collectionName, it)
                            }
                        }
                        collectionName == "Popular Movies" -> {
                            val media = mutableListOf<MediaItem>()
                            movieRepository.popularMovies()
                                .take(20)
                                .onEach { media.add(it) }
                                .collect()
                            media.forEach {
                                collectionRepo.addMediaToCollection(collectionName, it)
                            }
                        }
                        collectionName == "Popular TV Shows" -> {
                            val media = mutableListOf<MediaItem>()
                            tvRepository.popularTV()
                                .take(20)
                                .onEach { media.add(it) }
                                .collect()
                            media.forEach {
                                collectionRepo.addMediaToCollection(collectionName, it)
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        val PRE_PACKAGED_COLLECTIONS =
            listOf("Favorites", "Popular Movies", "Popular TV Shows")
    }
}