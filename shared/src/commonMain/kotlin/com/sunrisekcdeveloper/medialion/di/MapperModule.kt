package com.sunrisekcdeveloper.medialion.di

import com.sunrisekcdeveloper.medialion.MediaItemUI
import com.sunrisekcdeveloper.medialion.clients.models.MediaResponse
import com.sunrisekcdeveloper.medialion.di.MapperNames.mediaDomainToUI
import com.sunrisekcdeveloper.medialion.di.MapperNames.movieCacheToDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.movieDomainToCache
import com.sunrisekcdeveloper.medialion.di.MapperNames.movieDomainToMediaDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.movieDomainToUI
import com.sunrisekcdeveloper.medialion.di.MapperNames.movieResponseToDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvCacheToDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvDomainToCache
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvDomainToMediaDomain
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvDomainToUI
import com.sunrisekcdeveloper.medialion.di.MapperNames.tvResponseToDomain
import com.sunrisekcdeveloper.medialion.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.mappers.Mapper
import database.MovieCache
import database.TVShowCache
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mapperModule = module {
    factory<Mapper<MediaResponse, Movie>>(named(movieResponseToDomain)) { Mapper.MovieEntity.ResponseToDomain() }
    factory<Mapper<Movie, MovieCache>>(named(movieDomainToCache)) { Mapper.MovieEntity.DomainToCache() }
    factory<Mapper<MovieCache, Movie>>(named(movieCacheToDomain)) { Mapper.MovieEntity.CacheToDomain() }
    factory<Mapper<Movie, MediaItem>>(named(movieDomainToMediaDomain)) { Mapper.MovieEntity.DomainToMediaDomain() }
    factory<Mapper<Movie, MediaItemUI>>(named(movieDomainToUI)) { Mapper.MovieEntity.DomainToUI() }
    factory<Mapper<MediaResponse, TVShow>>(named(tvResponseToDomain)) { Mapper.TVShowEntity.ResponseToDomain() }
    factory<Mapper<TVShow, TVShowCache>>(named(tvDomainToCache)) { Mapper.TVShowEntity.DomainToCache() }
    factory<Mapper<TVShowCache, TVShow>>(named(tvCacheToDomain)) { Mapper.TVShowEntity.CacheToDomain() }
    factory<Mapper<TVShow, MediaItemUI>>(named(tvDomainToUI)) { Mapper.TVShowEntity.DomainToUI() }
    factory<Mapper<TVShow, MediaItem>>(named(tvDomainToMediaDomain)) { Mapper.TVShowEntity.DomainToMediaDomain() }
    factory<Mapper<MediaItem, MediaItemUI>>(named(mediaDomainToUI)) { Mapper.DomainToUI() }
}

object MapperNames {
    const val movieResponseToDomain = "movieResponseToDomain"
    const val movieDomainToCache = "movieDomainToCache"
    const val movieCacheToDomain = "movieCacheToDomain"
    const val movieDomainToMediaDomain = "movieDomainToMediaDomain"
    const val movieDomainToUI = "movieDomainToUI"
    const val tvResponseToDomain = "tvResponseToDomain"
    const val tvDomainToCache = "tvDomainToCache"
    const val tvCacheToDomain = "tvCacheToDomain"
    const val tvDomainToMediaDomain = "tvDomainToMediaDomain"
    const val mediaDomainToUI = "mediaDomainToUI"
    const val tvDomainToUI = "tvDomainToUI"
}