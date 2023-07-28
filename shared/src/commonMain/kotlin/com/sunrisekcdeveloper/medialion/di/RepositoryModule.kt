package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.MediaCategoryRepository
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.MediaRequirementsRepository
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.TitledMediaRepository
import com.sunrisekcdeveloper.medialion.components.shared.data.collection.CollectionLocalDataSource
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryLocalDataSource
import com.sunrisekcdeveloper.medialion.components.shared.data.mediaCategory.MediaCategoryRemoteDataSource
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.SingleMediaLocalDataSource
import com.sunrisekcdeveloper.medialion.components.shared.data.singleMedia.SingleMediaRemoteDataSource
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.SingleMediaItemRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single<MediaCategoryRepository> {
        MediaCategoryRepository.D(
            localDataSource = get<MediaCategoryLocalDataSource>(),
            remoteDataSource = get<MediaCategoryRemoteDataSource>(),
            apiMapper = get(named(MapperNames.MediaCategoryMapperNames.apiDtoToDomain)),
            entityMapper = get(named(MapperNames.MediaCategoryMapperNames.entityDtoToDomain)),
            domainMapper = get(named(MapperNames.MediaCategoryMapperNames.domainToEntityDto))
        )
    }

    single<MediaRequirementsRepository> {
        MediaRequirementsRepository.D(get<MediaCategoryRepository>())
    }

    single<SingleMediaItemRepository> {
        SingleMediaItemRepository.D(
            remoteDataSource = get<SingleMediaRemoteDataSource>(),
            localDataSource = get<SingleMediaLocalDataSource>(),
            dtoMapper = get(named(MapperNames.SingleMediaItemNames.apiDtoToDomain)),
            categoryRepository = get<MediaCategoryRepository>(),
        )
    }

    single<CollectionRepositoryNew> {
        CollectionRepositoryNew.D(
            localDataSource = get<CollectionLocalDataSource>(),
            entityMapper = get(named(MapperNames.CollectionNames.entityDtoToDomain)),
            domainMapper = get(named(MapperNames.CollectionNames.domainToEntityDto)),
        )
    }

    single<TitledMediaRepository> {
        TitledMediaRepository.D(get<SingleMediaItemRepository>())
    }
}