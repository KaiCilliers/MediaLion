package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaCategory
import com.sunrisekcdeveloper.medialion.components.shared.data.collection.CollectionEntityDto
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryApiDto
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryEntityDto
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.SingleMediaApiDto
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.CollectionNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.models.SingleMediaItem
import com.sunrisekcdeveloper.medialion.network.models.MediaResponse
import com.sunrisekcdeveloper.medialion.utils.mappers.Mapper
import database.CategoryCache
import database.MovieCache
import database.TVShowCache
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mapperModule = module {
    // region MediaCategory
    factory<Mapper<CategoryCache, MediaCategoryEntityDto>>(named(MapperNames.MediaCategoryMapperNames.cacheToEntityDto)) { Mapper.MediaCategoryMappers.CacheToEntityDto() }
    factory<Mapper<MediaCategoryEntityDto, CategoryCache>>(named(MapperNames.MediaCategoryMapperNames.entityDtoToCache)) { Mapper.MediaCategoryMappers.EntityDtoToCache() }
    factory<Mapper<MediaCategoryApiDto, MediaCategory>>(named(MapperNames.MediaCategoryMapperNames.apiDtoToDomain)) { Mapper.MediaCategoryMappers.ApiDtoToDomain() }
    factory<Mapper<MediaCategoryEntityDto, MediaCategory>>(named(MapperNames.MediaCategoryMapperNames.entityDtoToDomain)) { Mapper.MediaCategoryMappers.EntityDtoToDomain() }
    factory<Mapper<MediaCategory, MediaCategoryEntityDto>>(named(MapperNames.MediaCategoryMapperNames.domainToEntityDto)) { Mapper.MediaCategoryMappers.DomainToEntityDto() }
    // endregion

    // region SingleMediaItem
    factory<Mapper<MediaResponse, SingleMediaApiDto>>(named(MapperNames.SingleMediaItemNames.responseToSingleMediaApiDto)) { Mapper.SingleMediaItemMappers.ResponseToApiDto() }
    factory<Mapper<SingleMediaApiDto, SingleMediaItem>>(named(MapperNames.SingleMediaItemNames.apiDtoToDomain)) { Mapper.SingleMediaItemMappers.ApiDtoToDomain() }
    factory<Mapper<MovieCache, SingleMediaItem.Movie>>(named(MapperNames.SingleMediaItemNames.movieCacheToDomain)) { Mapper.SingleMediaItemMappers.MovieCacheToDomain() }
    factory<Mapper<TVShowCache, SingleMediaItem.TVShow>>(named(MapperNames.SingleMediaItemNames.tvShowCacheToDomain)) { Mapper.SingleMediaItemMappers.TVShowCacheToDomain() }
    factory<Mapper<SingleMediaItem.Movie, MovieCache>>(named(MapperNames.SingleMediaItemNames.domainToMovieCache)) { Mapper.SingleMediaItemMappers.DomainToMovieCache() }
    factory<Mapper<SingleMediaItem.TVShow, TVShowCache>>(named(MapperNames.SingleMediaItemNames.domainToTVShowCache)) { Mapper.SingleMediaItemMappers.DomainToTVShowCache() }
    // endregion

    // region Collection
    factory<Mapper<CollectionEntityDto, CollectionNew>>(named(MapperNames.CollectionNames.entityDtoToDomain)) { Mapper.CollectionMappers.EntityDtoToDomain() }
    factory<Mapper<CollectionNew, CollectionEntityDto>>(named(MapperNames.CollectionNames.domainToEntityDto)) { Mapper.CollectionMappers.DomainToEntityDto() }
    // endregion

}

object MapperNames {

    object MediaCategoryMapperNames {
        const val cacheToEntityDto = "MC_cacheToEntityDto"
        const val entityDtoToCache = "MC_entityDtoToCache"
        const val apiDtoToDomain = "MC_apiDtoToDomain"
        const val entityDtoToDomain = "MC_entityDtoToDomain"
        const val domainToEntityDto = "MC_domainToEntityDto"
    }

    object SingleMediaItemNames {
        const val responseToSingleMediaApiDto = "SMI_responseToSingleMediaApiDto"
        const val apiDtoToDomain = "SMI_apiDtoToDomain"
        const val movieCacheToDomain = "SMI_movieCacheToDomain"
        const val tvShowCacheToDomain = "SMI_tvShowCacheToDomain"
        const val domainToMovieCache = "SMI_domainToMovieCache"
        const val domainToTVShowCache = "SMI_domainToTVShowCache"
    }

    object CollectionNames {
        const val entityDtoToDomain = "entityDtoToDomain"
        const val domainToEntityDto = "domainToEntityDto"
    }
}