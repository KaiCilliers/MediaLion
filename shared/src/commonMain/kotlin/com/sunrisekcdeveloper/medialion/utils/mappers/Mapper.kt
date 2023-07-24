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
import com.sunrisekcdeveloper.medialion.oldArch.domain.value.Title
import database.CategoryCache
import database.MovieCache
import database.TVShowCache
import io.github.aakira.napier.Napier

interface Mapper<I, O> {
    fun map(input: I): O

    object MediaCategoryMappers {
        class CacheToEntityDto : Mapper<CategoryCache, MediaCategoryEntityDto> {
            override fun map(input: CategoryCache): MediaCategoryEntityDto {
                return MediaCategoryEntityDto(input.id)
            }

        }

        class EntityDtoToCache : Mapper<MediaCategoryEntityDto, CategoryCache> {
            override fun map(input: MediaCategoryEntityDto): CategoryCache {
                return CategoryCache(
                    id = input.placeholder, name = input.placeholder, appliesToMedia = input.placeholder
                )
            }

        }

        class ApiDtoToDomain : Mapper<MediaCategoryApiDto, MediaCategory> {
            override fun map(input: MediaCategoryApiDto): MediaCategory {
                return MediaCategory.D(name = input.name, appliesToType = input.appliesTo)
            }

        }

        class EntityDtoToDomain : Mapper<MediaCategoryEntityDto, MediaCategory> {
            override fun map(input: MediaCategoryEntityDto): MediaCategory {
                return MediaCategory.D(name = input.placeholder, appliesToType = MediaTypeNew.All)
            }

        }

        class DomainToEntityDto : Mapper<MediaCategory, MediaCategoryEntityDto> {
            override fun map(input: MediaCategory): MediaCategoryEntityDto {
                return MediaCategoryEntityDto(input.name())
            }

        }
    }

    object SingleMediaItemMappers {
        class ResponseToApiDto : Mapper<MediaResponse, SingleMediaApiDto> {
            override fun map(input: MediaResponse): SingleMediaApiDto {
                return if (input.firstAirDate != null) {
                    SingleMediaApiDto.TVShow(
                        id = input.id!!.toString(),
                        title = input.title ?: input.originalTitle!!,
                        firstAirDate = input.firstAirDate
                    )
                } else {
                    SingleMediaApiDto.Movie(
                        id = input.id!!.toString(),
                        title = input.title ?: input.originalTitle!!,
                        releaseDate = input.releaseDate!!
                    )
                }
            }
        }

        class ApiDtoToDomain : Mapper<SingleMediaApiDto, SingleMediaItem> {
            override fun map(input: SingleMediaApiDto): SingleMediaItem {
                return when (input) {
                    is SingleMediaApiDto.Movie -> SingleMediaItem.Movie(
                        id = ID.Def(input.id),
                        title = Title(input.title)
                    )

                    is SingleMediaApiDto.TVShow -> SingleMediaItem.TVShow(
                        id = ID.Def(input.id),
                        title = Title(input.title)
                    )
                }
            }

        }

        class MovieCacheToDomain : Mapper<MovieCache, SingleMediaItem.Movie> {
            override fun map(input: MovieCache): SingleMediaItem.Movie {
                return SingleMediaItem.Movie(
                    id = ID.Def(input.id),
                    title = Title(input.title)
                )
            }

        }

        class TVShowCacheToDomain : Mapper<TVShowCache, SingleMediaItem.TVShow> {
            override fun map(input: TVShowCache): SingleMediaItem.TVShow {
                return SingleMediaItem.TVShow(
                    id = ID.Def(input.id),
                    title = Title(input.name),
                )
            }

        }

        class DomainToMovieCache : Mapper<SingleMediaItem.Movie, MovieCache> {
            override fun map(input: SingleMediaItem.Movie): MovieCache {
                return MovieCache(
                    id = input.id.uniqueIdentifier(),
                    adult = false,
                    backdrop_path = "",
                    genre_ids = listOf(),
                    overview = "",
                    popularity = 0.0,
                    poster_path = "",
                    release_date = null,
                    title = input.title.value,
                    vote_average = 0.0,
                    vote_count = 0
                )
            }

        }

        class DomainToTVShowCache : Mapper<SingleMediaItem.TVShow, TVShowCache> {
            override fun map(input: SingleMediaItem.TVShow): TVShowCache {
                return TVShowCache(
                    id = input.id.uniqueIdentifier(),
                    name = input.title.value,
                    backdropPath = "",
                    genre_ids = listOf(),
                    overview = "",
                    popularity = 0.0,
                    poster_path = "",
                    vote_average = 0.0,
                    vote_count = 0,
                    first_air_date = "",
                    adult = false
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
