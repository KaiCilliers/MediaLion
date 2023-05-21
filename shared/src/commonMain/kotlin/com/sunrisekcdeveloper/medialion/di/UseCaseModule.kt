package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.di.MapperNames.movieDomainToMediaDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvDomainToMediaDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvDomainToUI
import com.sunrisekcdeveloper.medialion.domain.search.usecases.CreateCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.SaveMediaToCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.DocumentariesRelatedToUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.FetchAllCollectionsUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.FetchCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.FetchDiscoveryContent
import com.sunrisekcdeveloper.medialion.domain.search.usecases.MovieDetailsUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.MoviesRelatedToUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.RemoveMediaFromCollectionUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.SuggestedMediaUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.TVDetailsUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.TVRelatedToUseCase
import com.sunrisekcdeveloper.medialion.domain.search.usecases.TopMediaResultsUseCase
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieDetailsUseCase> { MovieDetailsUseCase.Default(get()) }
    factory<TVDetailsUseCase> { TVDetailsUseCase.Default(get()) }
    factory<DocumentariesRelatedToUseCase> { DocumentariesRelatedToUseCase.Default(get()) }
    factory<MoviesRelatedToUseCase> { MoviesRelatedToUseCase.Default(get()) }
    factory<SuggestedMediaUseCase> { SuggestedMediaUseCase.Default(get()) }
    factory<TopMediaResultsUseCase> { TopMediaResultsUseCase.Default(get(), get(), get(named(tvDomainToMediaDomain)), get(named(movieDomainToMediaDomain))) }
    factory<TVRelatedToUseCase> { TVRelatedToUseCase.Default(get()) }
    factory<SaveMediaToCollectionUseCase> { SaveMediaToCollectionUseCase.Default(get(), get(), get()) }
    factory<CreateCollectionUseCase> { CreateCollectionUseCase.Default(get()) }
    factory<FetchAllCollectionsUseCase> { FetchAllCollectionsUseCase.Default(get()) }
    factory<FetchCollectionUseCase> { FetchCollectionUseCase.Default(get()) }
    factory<RemoveMediaFromCollectionUseCase> { RemoveMediaFromCollectionUseCase.Default(get()) }
    factory<FetchDiscoveryContent> { FetchDiscoveryContent.Default(get(), ListMapper.Impl(get(named(tvDomainToUI)))) }
}