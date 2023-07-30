package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.components.shared.TMDBClientNew
import com.sunrisekcdeveloper.medialion.components.shared.data.collection.CollectionLocalDataSource
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryLocalDataSource
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryRemoteDataSource
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.SingleMediaLocalDataSource
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.SingleMediaRemoteDataSource
import com.sunrisekcdeveloper.medialion.database.MediaLionDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {

    // region MediaCategory
    factory<MediaCategoryLocalDataSource> {
        MediaCategoryLocalDataSource.D(
            db = get<MediaLionDatabase>(),
            cacheMapper = get(named(MapperNames.MediaCategoryMapperNames.cacheToEntityDto)),
            entityMapper = get(named(MapperNames.MediaCategoryMapperNames.entityDtoToCache)),
        )
    }

    factory<MediaCategoryRemoteDataSource> {
        MediaCategoryRemoteDataSource.D(apiClient = get<TMDBClientNew>())
    }
    // endregion

    // region Collection
    factory<CollectionLocalDataSource> {
        CollectionLocalDataSource.D(
            db = get<MediaLionDatabase>(),
            movieCacheMapper = get(named(MapperNames.SingleMediaItemNames.movieCacheToDomain)),
            tvCacheMapper = get(named(MapperNames.SingleMediaItemNames.tvShowCacheToDomain)),
            movieDomainMapper = get(named(MapperNames.SingleMediaItemNames.domainToMovieCache)),
            tvDomainMapper = get(named(MapperNames.SingleMediaItemNames.domainToTVShowCache)),
            mediaCategoryMapper = get(named(MapperNames.MediaCategoryMapperNames.cacheToEntityDto)),
            mediaCategoryDomainMapper = get(named(MapperNames.MediaCategoryMapperNames.entityDtoToDomain)),
        )
    }
    // endregion

    // region SingleMediaItem
    factory<SingleMediaLocalDataSource> {
        SingleMediaLocalDataSource.D(
            db = get<MediaLionDatabase>(),
            domainMovieMapper = get(named(MapperNames.SingleMediaItemNames.domainToMovieCache)),
            domainTVShowMapper = get(named(MapperNames.SingleMediaItemNames.domainToTVShowCache)),
            cacheMovieMapper = get(named(MapperNames.SingleMediaItemNames.movieCacheToDomain)),
            cacheTVShowMapper = get(named(MapperNames.SingleMediaItemNames.tvShowCacheToDomain)),
            mediaCategoryMapper = get(named(MapperNames.MediaCategoryMapperNames.cacheToEntityDto)),
            mediaCategoryDomainMapper = get(named(MapperNames.MediaCategoryMapperNames.entityDtoToDomain)),
        )
    }
    factory<SingleMediaRemoteDataSource> {
        SingleMediaRemoteDataSource.D(
            mediaApiClient = get<TMDBClientNew>(),
        )
    }
    // endregion


}