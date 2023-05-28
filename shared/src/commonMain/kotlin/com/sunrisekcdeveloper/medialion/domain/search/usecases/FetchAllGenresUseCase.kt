package com.sunrisekcdeveloper.medialion.domain.search.usecases

import com.sunrisekcdeveloper.medialion.clients.TMDBClient
import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.value.Genre
import io.github.aakira.napier.log

interface FetchAllGenresUseCase {
    suspend operator fun invoke(): List<Genre>
    class Default(
        val api: TMDBClient
    ) : FetchAllGenresUseCase {
        override suspend fun invoke(): List<Genre> {
            val allGenres: MutableList<Genre> = mutableListOf()
            api.tvGenres()
                .onFailure { log { "Failed to fetch TV Genres" }; throw it }
                .onSuccess { genreWrapper ->
                    genreWrapper.genres.map {
                        Genre(
                            id = it.id,
                            name = it.name,
                            mediaType = MediaType.TV
                        ).also {
                            allGenres.add(it)
                        }
                    }
                }

            api.movieGenres()
                .onFailure { log { "Failed to fetch Movie Genres" }; throw it }
                .onSuccess { genreWrapper ->
                    genreWrapper.genres.map {
                        Genre(
                            id = it.id,
                            name = it.name,
                            mediaType = MediaType.MOVIE
                        ).also {
                            allGenres.add(it)
                        }
                    }
                }
            return allGenres
        }
    }
}