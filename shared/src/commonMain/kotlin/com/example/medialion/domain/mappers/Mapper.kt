package com.example.medialion.domain.mappers

import com.example.medialion.data.NetworkConstants
import com.example.medialion.data.models.MovieDetailResponse
import com.example.medialion.data.models.MovieListResponse
import com.example.medialion.data.models.MultiTypeResponse
import com.example.medialion.domain.components.search.wip.MappingException
import com.example.medialion.domain.components.search.wip.domain.MovieDetail
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieUiModel

interface Mapper<I, O> {
    fun map(input: I): O

    class MovieDataMapper : Mapper<MovieListResponse, Movie> {
        override fun map(input: MovieListResponse): Movie {
            return Movie(
                backdropPath = NetworkConstants.BASE_IMAGE_URL_TMDB + input.backdropPath.orEmpty(),
                genreIds = input.genreIds!!,
                id = input.id!!,
                language = input.originalLanguage!!,
                overview = input.overview!!,
                popularity = input.popularity!!,
                posterPath = NetworkConstants.BASE_IMAGE_URL_TMDB + input.posterPath,
                releaseDate = input.releaseDate!!,
                title = input.title!!
            )
        }
    }

    class MovieUiMapper : Mapper<Movie, MovieUiModel> {
        override fun map(input: Movie): MovieUiModel {
            return MovieUiModel(
                id = input.id,
                title = input.title,
                isFavorited = false,
                description = input.overview,
                year = input.releaseDate,
                posterUrl = input.posterPath.orEmpty(),
                bannerUrl = input.backdropPath.orEmpty(),
            )
        }
    }

    class MovieDetailResponseToDomain : Mapper<MovieDetailResponse, MovieDetail> {
        override fun map(input: MovieDetailResponse): MovieDetail {
            return try {
                MovieDetail(
                    adult = input.adult ?: true,
                    backdropPath = input.backdropPath.orEmpty(),
                    budget = input.budget ?: 0,
                    genres = input.genres ?: emptyList(),
                    id = input.id!!,
                    overview = input.overview!!,
                    popularity = input.popularity!!,
                    posterPath = input.posterPath.orEmpty(),
                    releaseDate = input.releaseDate,
                    revenue = input.revenue ?: 0,
                    runtime = input.runtime!!,
                    tagline = input.tagline ?: "",
                    title = input.title ?: input.originalTitle!!,
                    voteAverage = input.voteAverage ?: 0.0,
                    voteCount = input.voteCount ?: 0,
                )
            } catch (e: Exception) {
                throw MappingException(e.message, e.cause)
            }
        }
    }

    class MovieResponseToDomain : Mapper<MovieListResponse, com.example.medialion.domain.components.search.wip.domain.Movie> {
        override fun map(input: MovieListResponse): com.example.medialion.domain.components.search.wip.domain.Movie {
            return try {
                com.example.medialion.domain.components.search.wip.domain.Movie(
                    adult = input.adult ?: true,
                    backdropPath = input.backdropPath.orEmpty(),
                    genreIds = input.genreIds ?: emptyList(),
                    id = input.id!!,
                    overview = input.overview!!,
                    popularity = input.popularity!!,
                    posterPath = input.posterPath.orEmpty(),
                    releaseDate = input.releaseDate,
                    title = input.title ?: input.originalTitle!!,
                    voteAverage = input.voteAverage ?: 0.0,
                    voteCount = input.voteCount ?: 0,
                )
            } catch (e: Exception) {
                throw MappingException(e.message, e.cause)
            }
        }
    }
}