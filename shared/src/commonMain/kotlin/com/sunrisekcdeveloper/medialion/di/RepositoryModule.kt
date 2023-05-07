package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.di.MapperNames.movieCacheToDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.movieDomainToCache
import com.sunrisekcdeveloper.medialion.di.MapperNames.movieDomainToMediaDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.movieResponseToDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvCacheToDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvDomainToCache
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvDomainToMediaDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvResponseToDomain
import com.sunrisekcdeveloper.medialion.mappers.ListMapper
import com.sunrisekcdeveloper.medialion.repos.CollectionRepository
import com.sunrisekcdeveloper.medialion.repos.MovieRepository
import com.sunrisekcdeveloper.medialion.repos.TVRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> {
        MovieRepository.Default(
            database = get(),
            client = get(),
            dispatcherProvider = get(),
            responseToDomain = get(named(movieResponseToDomain)),
            domainToCache = get(named(movieDomainToCache)),
            cacheToDomain = get(named(movieCacheToDomain)),
            responseToDomainListMapper = ListMapper.Impl(get(named(movieResponseToDomain))),
        )
    }
    single<TVRepository> {
        TVRepository.Default(
            database = get(),
            client = get(),
            dispatcherProvider = get(),
            responseToDomain = get(named(tvResponseToDomain)),
            domainToCache = get(named(tvDomainToCache)),
            cacheToDomain = get(named(tvCacheToDomain)),
            responseToDomainListMapper = ListMapper.Impl(get(named(tvResponseToDomain)))
        )
    }
    single<CollectionRepository> {
        CollectionRepository.Default(
            database = get(),
            dispatcherProvider = get(),
            movieCacheToDomainList = ListMapper.Impl(get(named(movieCacheToDomain))),
            tvShowCacheToDomainList = ListMapper.Impl(get(named(tvCacheToDomain))),
            movieDomainToMediaDomain = ListMapper.Impl(get(named(movieDomainToMediaDomain))),
            tvShowDomainToMediaDomain = ListMapper.Impl(get(named(tvDomainToMediaDomain))),
        )
    }
}