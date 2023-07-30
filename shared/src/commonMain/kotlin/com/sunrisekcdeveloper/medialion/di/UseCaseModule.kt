package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.components.collections.domain.AddUpdateCollectionUseCase
import com.sunrisekcdeveloper.medialion.components.collections.domain.DeleteCollectionUseCaseNew
import com.sunrisekcdeveloper.medialion.components.collections.domain.FetchAllCollectionsAsTitledMediaUseCase
import com.sunrisekcdeveloper.medialion.components.collections.domain.InsertDefaultCollectionsUseCase
import com.sunrisekcdeveloper.medialion.components.collections.domain.ObserveAllCollectionsUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchDiscoveryContentUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchMediaWithCategoryUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.FetchSuggestedMediaUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.SearchForMediaUseCase
import com.sunrisekcdeveloper.medialion.components.discovery.domain.models.MediaRequirementsFactory
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.MediaCategoryRepository
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.MediaRequirementsRepository
import com.sunrisekcdeveloper.medialion.components.discovery.domain.repo.TitledMediaRepository
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllCollectionsUseCaseNew
import com.sunrisekcdeveloper.medialion.components.shared.domain.FetchAllMediaCategoriesUseCase
import com.sunrisekcdeveloper.medialion.components.shared.domain.repos.CollectionRepositoryNew
import org.koin.dsl.module

val useCaseModule = module {
    factory<InsertDefaultCollectionsUseCase> {
        InsertDefaultCollectionsUseCase.Def(get<CollectionRepositoryNew>())
    }
    factory<AddUpdateCollectionUseCase> {
        AddUpdateCollectionUseCase.Def(get<CollectionRepositoryNew>())
    }
    factory<DeleteCollectionUseCaseNew> {
        DeleteCollectionUseCaseNew.Def(get<CollectionRepositoryNew>())
    }
    factory<FetchAllCollectionsAsTitledMediaUseCase> {
        FetchAllCollectionsAsTitledMediaUseCase.Default(get<CollectionRepositoryNew>())
    }
    factory<ObserveAllCollectionsUseCase> {
        ObserveAllCollectionsUseCase.Def(get<CollectionRepositoryNew>())
    }
    factory<FetchDiscoveryContentUseCase> {
        FetchDiscoveryContentUseCase.D(
            get<MediaRequirementsRepository>(),
            get<TitledMediaRepository>()
        )
    }
    factory<FetchMediaWithCategoryUseCase> {
        FetchMediaWithCategoryUseCase.D(
            get<TitledMediaRepository>(),
            get<MediaRequirementsFactory>(),
        )
    }
    factory<FetchSuggestedMediaUseCase> {
        FetchSuggestedMediaUseCase.Def(
            get<MediaRequirementsFactory>(),
            get<TitledMediaRepository>(),
            get<CollectionRepositoryNew>(),
        )
    }
    factory<SearchForMediaUseCase> {
        SearchForMediaUseCase.Def(
            get<TitledMediaRepository>(),
            get<MediaRequirementsFactory>(),
        )
    }
    factory<FetchAllMediaCategoriesUseCase> {
        FetchAllMediaCategoriesUseCase.D(get<MediaCategoryRepository>())
    }
    factory<FetchAllCollectionsUseCaseNew> {
        FetchAllCollectionsUseCaseNew.Default(get<CollectionRepositoryNew>())
    }
}