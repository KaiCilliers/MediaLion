package com.example.medialion.domain.mappers

import com.example.medialion.data.NetworkConstants
import com.example.medialion.data.searchComponent.MediaResponse
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieUiModel

interface Mapper<I, O> {
    fun map(input: I): O

    class MovieDataMapper : Mapper<MediaResponse, Movie> {
        override fun map(input: MediaResponse): Movie {
            return Movie(
                backdropPath = NetworkConstants.BASE_IMAGE_URL_TMDB + input.backdropPath.orEmpty(),
                genreIds = input.genreIds,
                id = input.id,
                language = input.originalLanguage,
                overview = input.overview,
                popularity = input.popularity,
                posterPath = NetworkConstants.BASE_IMAGE_URL_TMDB + input.posterPath,
                releaseDate = input.releaseDate,
                title = input.title
            )
        }
    }

    class MovieUiMapper : Mapper<Movie, MovieUiModel> {
        override fun map(input: Movie): MovieUiModel {
            return MovieUiModel(
                id = input.id,
                title = input.title,
                isFavorited = false,
                posterUrl = input.posterPath.orEmpty(),
                bannerUrl = input.backdropPath.orEmpty(),
            )
        }
    }
}