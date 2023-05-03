package com.example.medialion.domain.mappers

import com.example.medialion.data.NetworkConstants
import com.example.medialion.data.models.MovieDetailResponse
import com.example.medialion.data.models.MovieListResponse
import com.example.medialion.data.models.TVShowListResponse
import com.example.medialion.domain.models.MediaItem
import com.example.medialion.domain.models.MediaItemUI
import com.example.medialion.domain.models.MediaType
import com.example.medialion.domain.models.MovieDetail
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.OldMovie
import com.example.medialion.domain.models.TVShow
import database.MovieEntity

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
                revenue = input.revenue ?: 0L,
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

    class MovieEntityToDomain : Mapper<MovieEntity, Movie> {
        override fun map(input: MovieEntity): Movie {
            return Movie(
                adult = input.adult,
                backdropPath = input.backdrop_path,
                genreIds = input.genre_ids,
                id = input.id,
                overview = input.overview,
                popularity = input.popularity,
                posterPath = input.poster_path,
                releaseDate = input.release_date,
                title = input.title,
                voteAverage = input.vote_average,
                voteCount = input.vote_count,
            )
        }
    }

    class MovieDetailEntityToDomain : Mapper<MovieEntity, MovieDetail> {
        override fun map(input: MovieEntity): MovieDetail {
            return MovieDetail(
                adult = input.adult,
                backdropPath = input.backdrop_path,
                budget = input.budget ?: 0,
                genres = emptyList(), // todo genre entity table to be able to return genres
                id = input.id,
                overview = input.overview,
                popularity = input.popularity,
                posterPath = input.poster_path,
                releaseDate = input.release_date,
                revenue = input.revenue ?: 0,
                runtime = input.runtime ?: 0,
                tagline = input.tagline.orEmpty(),
                title = input.title,
                voteAverage = input.vote_average,
                voteCount = input.vote_count,
            )
        }
    }

    class MovieDetailDomainToEntity : Mapper<MovieDetail, MovieEntity> {
        override fun map(input: MovieDetail): MovieEntity {
            return MovieEntity(
                adult = input.adult,
                backdrop_path = input.backdropPath,
                budget = input.budget,
                genre_ids = input.genres.map { it.id },
                id = input.id,
                overview = input.overview,
                popularity = input.popularity,
                poster_path = input.posterPath,
                release_date = input.releaseDate,
                revenue = input.revenue,
                runtime = input.runtime,
                tagline = input.tagline,
                title = input.title,
                vote_average = input.voteAverage,
                vote_count = input.voteCount,
            )
        }
    }

    class MovieDomainToEntity : Mapper<Movie, MovieEntity> {
        override fun map(input: Movie): MovieEntity {
            return MovieEntity(
                adult = input.adult,
                backdrop_path = input.backdropPath,
                genre_ids = input.genreIds,
                id = input.id,
                overview = input.overview,
                popularity = input.popularity,
                poster_path = input.posterPath,
                release_date = input.releaseDate,
                title = input.title,
                vote_average = input.voteAverage,
                vote_count = input.voteCount,
                budget = null,
                revenue = null,
                runtime = null,
                tagline = null,
            )
        }
    }

    class TVDomainToMediaUI : Mapper<TVShow, MediaItemUI> {
        override fun map(input: TVShow): MediaItemUI {
            return MediaItemUI(
                id = input.id,
                title = input.name,
                isFavorited = false,
                posterUrl = input.posterPath,
                bannerUrl = input.backdropPath,
                genreIds = input.genreIds,
                overview = input.overview,
                popularity = input.popularity,
                voteAverage = input.voteAverage,
                voteCount = input.voteCount,
                releaseYear = input.firstAirDate,
                mediaType = MediaType.TV,
            )
        }
    }

    class TVDomainToMediaDomain : Mapper<TVShow, MediaItem> {
        override fun map(input: TVShow): MediaItem {
            return MediaItem(
                id = input.id,
                title = input.name,
                genreIds = input.genreIds,
                overview = input.overview,
                popularity = input.popularity,
                voteAverage = input.voteAverage,
                voteCount = input.voteCount,
                releaseYear = input.firstAirDate,
                posterPath = input.posterPath,
                backdropPath = input.backdropPath,
                mediaType = MediaType.TV,
            )
        }
    }

    class MovieDomainToMediaUI : Mapper<Movie, MediaItemUI> {
        override fun map(input: Movie): MediaItemUI {
            return MediaItemUI(
                id = input.id,
                title = input.title,
                isFavorited = false,
                posterUrl = NetworkConstants.BASE_IMAGE_URL_TMDB + input.posterPath,
                bannerUrl = NetworkConstants.BASE_IMAGE_URL_TMDB + input.backdropPath,
                genreIds = input.genreIds,
                overview = input.overview,
                popularity = input.popularity,
                voteAverage = input.voteAverage,
                voteCount = input.voteCount,
                releaseYear = input.releaseDate.orEmpty(),
                mediaType = MediaType.MOVIE,

                )
        }
    }

    class MovieDomainToMediaDomain : Mapper<Movie, MediaItem> {
        override fun map(input: Movie): MediaItem {
            return MediaItem(
                id = input.id,
                title = input.title,
                genreIds = input.genreIds,
                overview = input.overview,
                popularity = input.popularity,
                voteAverage = input.voteAverage,
                voteCount = input.voteCount,
                releaseYear = input.releaseDate.orEmpty(),
                posterPath = input.posterPath,
                backdropPath = input.backdropPath,
                mediaType = MediaType.MOVIE,
            )
        }
    }

    class MediaDomainToMediaUI : Mapper<MediaItem, MediaItemUI> {
        override fun map(input: MediaItem): MediaItemUI {
            return MediaItemUI(
                id = input.id,
                title = input.title,
                isFavorited = false,
                posterUrl = NetworkConstants.BASE_IMAGE_URL_TMDB + input.posterPath,
                bannerUrl = NetworkConstants.BASE_IMAGE_URL_TMDB + input.backdropPath,
                genreIds = input.genreIds,
                overview = input.overview,
                popularity = input.popularity,
                voteAverage = input.voteAverage,
                voteCount = input.voteCount,
                releaseYear = input.releaseYear,
                mediaType = input.mediaType
            )
        }
    }
}