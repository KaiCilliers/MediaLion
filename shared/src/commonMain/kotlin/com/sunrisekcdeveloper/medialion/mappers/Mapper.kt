package com.sunrisekcdeveloper.medialion.mappers

import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.data.NetworkConstants
import com.sunrisekcdeveloper.medialion.clients.models.MediaResponse
import com.sunrisekcdeveloper.medialion.domain.MediaType
import com.sunrisekcdeveloper.medialion.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.domain.value.Date
import com.sunrisekcdeveloper.medialion.domain.value.ID
import com.sunrisekcdeveloper.medialion.domain.value.ImageURL
import com.sunrisekcdeveloper.medialion.domain.value.Overview
import com.sunrisekcdeveloper.medialion.domain.value.Rating
import com.sunrisekcdeveloper.medialion.domain.value.Title
import database.MovieCache
import database.TVShowCache

interface Mapper<I, O> {
    fun map(input: I): O

    object MovieEntity {
        class ResponseToDomain : Mapper<MediaResponse, Movie> {
            override fun map(input: MediaResponse): Movie {
                return try {
                    Movie(
                        adult = input.adult ?: false,
                        backdropPath = ImageURL(value = NetworkConstants.BASE_IMAGE_URL_TMDB + input.backdropPath.orEmpty()),
                        genreIds = input.genreIds?.map { ID(it) } ?: emptyList(),
                        id = ID(value = input.id!!),
                        overview = Overview(value = input.overview.orEmpty()),
                        popularity = Rating(value = input.popularity ?: 0.0),
                        posterPath = ImageURL(value = NetworkConstants.BASE_IMAGE_URL_TMDB + input.posterPath.orEmpty()),
                        releaseDate = Date(value = input.releaseDate.orEmpty()),
                        title = Title(value = input.title ?: input.originalTitle.orEmpty()),
                        voteAverage = Rating(value = input.voteAverage ?: 0.0),
                        voteCount = input.voteCount ?: 0
                    )
                } catch (e: Exception) {
                    throw Exception("Failed to map $input to domain", e)
                }
            }
        }

        class DomainToCache : Mapper<Movie, MovieCache> {
            override fun map(input: Movie): MovieCache {
                return MovieCache(
                    id = input.id.value,
                    adult = input.adult,
                    backdrop_path = input.backdropPath.value,
                    genre_ids = input.genreIds.map { it.value },
                    overview = input.overview.value,
                    popularity = input.popularity.value,
                    poster_path = input.posterPath.value,
                    release_date = input.releaseDate.value,
                    title = input.title.value,
                    vote_average = input.voteAverage.value,
                    vote_count = input.voteCount
                )
            }
        }

        class CacheToDomain : Mapper<MovieCache, Movie> {
            override fun map(input: MovieCache): Movie {
                return Movie(
                    adult = input.adult,
                    backdropPath = ImageURL(value = input.backdrop_path),
                    genreIds = input.genre_ids.map { ID(it) },
                    id = ID(value = input.id),
                    overview = Overview(value = input.overview),
                    popularity = Rating(value = input.popularity),
                    posterPath = ImageURL(value = input.poster_path),
                    releaseDate = Date(value = input.release_date.orEmpty()),
                    title = Title(value = input.title),
                    voteAverage = Rating(value = input.vote_average),
                    voteCount = input.vote_count
                )
            }
        }

        class DomainToMediaDomain : Mapper<Movie, MediaItem> {
            override fun map(input: Movie): MediaItem {
                return input
            }
        }

        class DomainToUI : Mapper<Movie, MediaItemUI> {
            override fun map(input: Movie): MediaItemUI {
                return MediaItemUI(
                    id = input.id.value,
                    title = input.title.value,
                    isFavorited = false,
                    posterUrl = input.posterPath.value,
                    bannerUrl = input.backdropPath.value,
                    genreIds = input.genreIds.map { it.value },
                    overview = input.overview.value,
                    popularity = input.popularity.value,
                    voteAverage = input.voteAverage.value,
                    voteCount = input.voteCount,
                    releaseYear = input.releaseDate.value,
                    mediaType = MediaType.MOVIE,
                )
            }
        }
    }

    object TVShowEntity {
        class ResponseToDomain : Mapper<MediaResponse, TVShow> {
            override fun map(input: MediaResponse): TVShow {
                println("I am going to map $input")
                try {
                    return TVShow(
                        adult = input.adult ?: false,
                        backdropPath = ImageURL(value = NetworkConstants.BASE_IMAGE_URL_TMDB + input.backdropPath.orEmpty()),
                        genreIds = input.genreIds?.map { ID(it) } ?: emptyList(),
                        id = ID(value = input.id!!),
                        overview = Overview(value = input.overview.orEmpty()),
                        popularity = Rating(value = input.popularity ?: 0.0),
                        posterPath = ImageURL(value = NetworkConstants.BASE_IMAGE_URL_TMDB + input.posterPath.orEmpty()),
                        name = Title(value = input.name ?: input.originalName.orEmpty()),
                        voteAverage = Rating(value = input.voteAverage ?: 0.0),
                        voteCount = input.voteCount ?: 0,
                        firstAirDate = Date(value = input.firstAirDate.orEmpty()),
                    )
                } catch (e: Exception) {
                    throw Exception("Failed to map $input to domain", e)
                }
            }
        }

        class DomainToCache : Mapper<TVShow, TVShowCache> {
            override fun map(input: TVShow): TVShowCache {
                return TVShowCache(
                    id = input.id.value,
                    name = input.name.value,
                    backdropPath = input.backdropPath.value,
                    genre_ids = input.genreIds.map { it.value },
                    overview = input.overview.value,
                    popularity = input.popularity.value,
                    poster_path = input.posterPath.value,
                    vote_average = input.voteAverage.value,
                    vote_count = input.voteCount,
                    first_air_date = input.firstAirDate.value,
                    adult = input.adult,
                )
            }
        }

        class CacheToDomain : Mapper<TVShowCache, TVShow> {
            override fun map(input: TVShowCache): TVShow {
                return TVShow(
                    adult = input.adult,
                    backdropPath = ImageURL(value = input.backdropPath),
                    genreIds = input.genre_ids.map { ID(it) },
                    id = ID(value = input.id),
                    overview = Overview(value = input.overview),
                    popularity = Rating(value = input.popularity),
                    posterPath = ImageURL(value = input.poster_path),
                    name = Title(value = input.name),
                    voteAverage = Rating(value = input.vote_average),
                    voteCount = input.vote_count,
                    firstAirDate = Date(value = input.first_air_date)
                )
            }
        }

        class DomainToMediaDomain : Mapper<TVShow, MediaItem> {
            override fun map(input: TVShow): MediaItem {
                return input
            }
        }

        class DomainToUI: Mapper<TVShow, MediaItemUI> {
            override fun map(input: TVShow): MediaItemUI {
                return MediaItemUI(
                    id = input.id.value,
                    title = input.name.value,
                    isFavorited = false,
                    posterUrl = input.posterPath.value,
                    bannerUrl = input.backdropPath.value,
                    genreIds = input.genreIds.map { it.value },
                    overview = input.overview.value,
                    popularity = input.popularity.value,
                    voteAverage = input.voteAverage.value,
                    voteCount = input.voteCount,
                    releaseYear = input.firstAirDate.value,
                    mediaType = MediaType.TV,

                )
            }
        }
    }

    class DomainToUI : Mapper<MediaItem, MediaItemUI> {
        override fun map(input: MediaItem): MediaItemUI {
            return when (input) {
                is Movie -> {
                    MediaItemUI(
                        id = input.id.value,
                        title = input.title.value,
                        isFavorited = false,
                        posterUrl = input.posterPath.value,
                        bannerUrl = input.backdropPath.value,
                        genreIds = input.genreIds.map { it.value },
                        overview = input.overview.value,
                        popularity = input.popularity.value,
                        voteAverage = input.voteAverage.value,
                        voteCount = input.voteCount,
                        releaseYear = input.releaseDate.value,
                        mediaType = MediaType.MOVIE,
                    )
                }

                is TVShow -> {
                    MediaItemUI(
                        id = input.id.value,
                        title = input.name.value,
                        isFavorited = false,
                        posterUrl = input.posterPath.value,
                        bannerUrl = input.backdropPath.value,
                        genreIds = input.genreIds.map { it.value },
                        overview = input.overview.value,
                        popularity = input.popularity.value,
                        voteAverage = input.voteAverage.value,
                        voteCount = input.voteCount,
                        releaseYear = input.firstAirDate.value,
                        mediaType = MediaType.TV,
                    )
                }
            }
        }
    }
}