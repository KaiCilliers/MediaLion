package com.example.medialion.domain.mappers

import com.example.medialion.data.NetworkConstants
import com.example.medialion.data.models.MovieDetailResponse
import com.example.medialion.data.models.MovieListResponse
import com.example.medialion.data.models.TVShowListResponse
import com.example.medialion.domain.models.MovieDetail
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.OldMovie
import com.example.medialion.domain.models.MovieUiModel
import com.example.medialion.domain.models.TVShow

interface Mapper<I, O> {
    fun map(input: I): O

    class MovieDataMapper : Mapper<MovieListResponse, OldMovie> {
        override fun map(input: MovieListResponse): OldMovie {
            return OldMovie(
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

    class MovieUiMapper : Mapper<OldMovie, MovieUiModel> {
        override fun map(input: OldMovie): MovieUiModel {
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
            return MovieDetail(
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
        }
    }

    class MovieResponseToDomain : Mapper<MovieListResponse, Movie> {
        override fun map(input: MovieListResponse): Movie {
            return Movie(
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
        }
    }

    class TVResponseToDomain : Mapper<TVShowListResponse, TVShow> {
        override fun map(input: TVShowListResponse): TVShow {
            return TVShow(
                backdropPath = input.backdropPath.orEmpty(),
                genreIds = input.genreIds ?: emptyList(),
                id = input.id!!,
                overview = input.overview!!,
                popularity = input.popularity!!,
                posterPath = input.posterPath.orEmpty(),
                name = input.name ?: input.originalName!!,
                voteAverage = input.voteAverage ?: 0.0,
                voteCount = input.voteCount ?: 0,
                firstAirDate = input.firstAirDate.orEmpty()
            )
        }
    }

    class MovieDomainToUi : Mapper<Movie, MovieUiModel> {
        override fun map(input: Movie): MovieUiModel {
            return MovieUiModel(
                id = input.id,
                title = input.title,
                isFavorited = false,
                description = input.overview,
                year = input.releaseDate?.take(4) ?: "Unreleased",
                posterUrl = NetworkConstants.BASE_IMAGE_URL_TMDB + input.posterPath,
                bannerUrl = NetworkConstants.BASE_IMAGE_URL_TMDB + input.backdropPath,
            )
        }
    }
}