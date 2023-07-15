package com.sunrisekcdeveloper.medialion.oldArch.di

import com.sunrisekcdeveloper.medialion.oldArch.MediaItemUI
import com.sunrisekcdeveloper.medialion.oldArch.clients.models.MediaResponse
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.mediaDomainToUI
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.movieCacheToDomain
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.movieDomainToCache
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.movieDomainToMediaDomain
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.movieDomainToUI
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.movieResponseToDomain
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.tvCacheToDomain
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.tvDomainToCache
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.tvDomainToMediaDomain
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.tvDomainToUI
import com.sunrisekcdeveloper.medialion.oldArch.di.MapperNames.tvResponseToDomain
import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.MediaItem
import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.Movie
import com.sunrisekcdeveloper.medialion.oldArch.domain.entities.TVShow
import com.sunrisekcdeveloper.medialion.oldArch.mappers.Mapper
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