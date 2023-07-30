package com.sunrisekcdeveloper.medialion.utils.mappers

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaTypeNew
import com.sunrisekcdeveloper.medialion.components.shared.data.collection.CollectionEntityDto
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryApiDto
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryEntityDto
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.SingleMediaApiDto
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.ID
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.network.models.MediaResponse
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Overview
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import database.CategoryCache
import database.MovieCache
import database.TVShowCache

interface Mapper<I, O> {
    fun map(input: I): O

    object MediaCategoryMappers {
        class CacheToEntityDto : Mapper<CategoryCache, MediaCategoryEntityDto> {
            override fun map(input: CategoryCache): MediaCategoryEntityDto {
                val appliedMedia = when (input.appliesToMedia) {
                    "Movies" -> MediaTypeNew.Movie
                    "TV Shows" -> MediaTypeNew.TVShow
                    else -> MediaTypeNew.All
                }
                return MediaCategoryEntityDto(id = ID.Def(input.id), name = input.name, appliesToMedia = appliedMedia)
            }

        }

        class EntityDtoToCache : Mapper<MediaCategoryEntityDto, CategoryCache> {
            override fun map(input: MediaCategoryEntityDto): CategoryCache {
                return CategoryCache(
                    id = input.id.uniqueIdentifier(),
                    name = input.name,
                    appliesToMedia = when(input.appliesToMedia) {
                        MediaTypeNew.All -> "All Media"
                        MediaTypeNew.Movie -> "Movies"
                        MediaTypeNew.TVShow -> "TV Shows"
                    }
                )
            }

        }

        class ApiDtoToDomain : Mapper<MediaCategoryApiDto, MediaCategory> {
            override fun map(input: MediaCategoryApiDto): MediaCategory {
                return MediaCategory.D(id = ID.Def(input.id), name = input.name, appliesToType = input.appliesTo)
            }

        }

        class EntityDtoToDomain : Mapper<MediaCategoryEntityDto, MediaCategory> {
            override fun map(input: MediaCategoryEntityDto): MediaCategory {
                return MediaCategory.D(id = input.id, name = input.name, appliesToType = input.appliesToMedia)
            }

        }

        class DomainToEntityDto : Mapper<MediaCategory, MediaCategoryEntityDto> {
            override fun map(input: MediaCategory): MediaCategoryEntityDto {
                return MediaCategoryEntityDto(id = input.identifier(), name = input.name(), appliesToMedia = input.typeAppliedTo())
            }

        }
    }

    object SingleMediaItemMappers {
        class ResponseToApiDto : Mapper<MediaResponse, SingleMediaApiDto> {
            override fun map(input: MediaResponse): SingleMediaApiDto {
                return if (input.firstAirDate != null) {
                    SingleMediaApiDto.TVShow(
                        id = input.id!!.toString(),
                        title = input.name ?: input.originalName!!,
                        firstAirDate = input.firstAirDate,
                        backdropPath = input.backdropPath!!,
                        posterPath = input.posterPath!!,
                        popularity = input.popularity ?: 0.0,
                        voteAverage = input.voteAverage ?: 0.0,
                        adult = input.adult ?: false,
                        overview = input.overview!!,
                        genreIds = input.genreIds!!,
                        voteCount = input.voteCount ?: 0,
                    )
                } else {
                    SingleMediaApiDto.Movie(
                        id = input.id!!.toString(),
                        title = input.title ?: input.originalTitle!!,
                        releaseDate = input.releaseDate!!,
                        backdropPath = input.backdropPath!!,
                        posterPath = input.posterPath!!,
                        popularity = input.popularity ?: 0.0,
                        voteAverage = input.voteAverage ?: 0.0,
                        adult = input.adult!!,
                        overview = input.overview!!,
                        genreIds = input.genreIds!!,
                        voteCount = input.voteCount ?: 0,
                    )
                }
            }
        }

        class ApiDtoToDomain : Mapper<SingleMediaApiDto, SingleMediaItem> {
            override fun map(input: SingleMediaApiDto): SingleMediaItem {
                return when (input) {
                    is SingleMediaApiDto.Movie -> SingleMediaItem.Movie(
                        id = ID.Def(input.id),
                        title = Title(input.title),
                        backdropPath = input.backdropPath,
                        posterPath = input.posterPath,
                        categories = listOf(), // TODO temporal coupling having to additionally add this data after mapping
                        popularity = input.popularity,
                        voteAverage = input.voteAverage,
                        adult = input.adult,
                        overview = Overview(input.overview),
                        voteCount = input.voteCount,
                        releaseDate = input.releaseDate,
                    )

                    is SingleMediaApiDto.TVShow -> SingleMediaItem.TVShow(
                        id = ID.Def(input.id),
                        title = Title(input.title),
                        backdropPath = input.backdropPath,
                        posterPath = input.posterPath,
                        categories = listOf(), // TODO temporal coupling having to additionally add this data after mapping
                        popularity = input.popularity,
                        voteAverage = input.voteAverage,
                        adult = input.adult,
                        overview = Overview(input.overview),
                        voteCount = input.voteCount,
                        firstAirDate = input.firstAirDate,
                    )
                }
            }

        }

        class MovieCacheToDomain : Mapper<MovieCache, SingleMediaItem.Movie> {
            override fun map(input: MovieCache): SingleMediaItem.Movie {
                return SingleMediaItem.Movie(
                    id = ID.Def(input.id),
                    title = Title(input.title),
                    backdropPath = input.backdrop_path,
                    posterPath = input.poster_path,
                    categories = emptyList(), // TODO temporal coupling having to additionally add this data after mapping
                    popularity = input.popularity,
                    voteAverage = input.vote_average,
                    adult = input.adult,
                    overview = Overview(input.overview),
                    releaseDate = input.release_date.orEmpty(), // todo wrap in object to handle missing release date for unreleased media
                    voteCount = input.vote_count,
                )
            }

        }

        class TVShowCacheToDomain : Mapper<TVShowCache, SingleMediaItem.TVShow> {
            override fun map(input: TVShowCache): SingleMediaItem.TVShow {
                return SingleMediaItem.TVShow(
                    id = ID.Def(input.id),
                    title = Title(input.name),
                    backdropPath = input.backdropPath,
                    posterPath = input.poster_path,
                    categories = emptyList(), // TODO temporal coupling having to additionally add this data after mapping
                    popularity = input.popularity,
                    voteAverage = input.vote_average,
                    adult = input.adult,
                    overview = Overview(input.overview),
                    firstAirDate = input.first_air_date,
                    voteCount = input.vote_count,
                )
            }

        }

        class DomainToMovieCache : Mapper<SingleMediaItem.Movie, MovieCache> {
            override fun map(input: SingleMediaItem.Movie): MovieCache {
                return MovieCache(
                    id = input.id.uniqueIdentifier(),
                    adult = input.adult,
                    backdrop_path = input.backdropPath,
                    genre_ids = input.categories.map { it.identifier().uniqueIdentifier().toInt() },
                    overview = input.overview.toString(),
                    popularity = input.popularity,
                    poster_path = input.posterPath,
                    release_date = input.releaseDate,
                    title = input.title.value,
                    vote_average = input.voteAverage,
                    vote_count = input.voteCount,
                )
            }

        }

        class DomainToTVShowCache : Mapper<SingleMediaItem.TVShow, TVShowCache> {
            override fun map(input: SingleMediaItem.TVShow): TVShowCache {
                return TVShowCache(
                    id = input.id.uniqueIdentifier(),
                    adult = input.adult,
                    backdropPath = input.backdropPath,
                    genre_ids = input.categories.map { it.identifier().uniqueIdentifier().toInt() },
                    overview = input.overview.toString(),
                    popularity = input.popularity,
                    poster_path = input.posterPath,
                    first_air_date = input.firstAirDate,
                    name = input.title.value,
                    vote_average = input.voteAverage,
                    vote_count = input.voteCount,
                )
            }

        }
    }

    object CollectionMappers {
        class EntityDtoToDomain : Mapper<CollectionEntityDto, CollectionNew> {
            override fun map(input: CollectionEntityDto): CollectionNew {
                return CollectionNew.Def(
                    id = input.id,
                    title = input.title,
                    media = input.media,
                )
            }

        }

        class DomainToEntityDto : Mapper<CollectionNew, CollectionEntityDto> {
            override fun map(input: CollectionNew): CollectionEntityDto {
                return CollectionEntityDto(
                    id = input.identifier(),
                    title = input.title(),
                    media = input.media(),
                )
            }

        }
    }
}
