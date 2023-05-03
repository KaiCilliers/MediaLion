package com.example.medialion.android.app

import android.app.Application
import android.content.Context
import com.example.medialion.data.HttpClientFactory
import com.example.medialion.data.clients.TMDBClient
import com.example.medialion.data.datasource.MovieRemoteDataSource
import com.example.medialion.data.datasource.TVRemoteDataSource
import com.example.medialion.data.repos.MovieRepository
import com.example.medialion.data.repos.TVRepository
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.mappers.Mapper
import com.example.medialion.local.DatabaseDriverFactory
import com.example.medialion.local.MediaLionDatabaseFactory
import com.example.medialion.local.MovieLocalDataSource
import com.example.medialion.local.TVLocalDataSource
import com.zhuinden.simplestack.GlobalServices
import com.zhuinden.simplestackextensions.servicesktx.add
import com.zhuinden.simplestackextensions.servicesktx.rebind
import kotlinx.coroutines.Dispatchers

class MediaLionApp : Application() {
    lateinit var globalServices: GlobalServices
        private set

    override fun onCreate() {
        super.onCreate()

        val tmdbClient = TMDBClient.Default(HttpClientFactory().create(), Dispatchers.IO)
        val movieRemoteDataSource = MovieRemoteDataSource.MovieRemoteDataSourceTMDB(
            apiClient = tmdbClient,
            movieDetailMapper = Mapper.MovieDetailResponseToDomain(),
            movieListMapper = ListMapper.Impl(Mapper.MovieResponseToDomain())
        )
        val tvRemoteDataSource = TVRemoteDataSource.Default(
            api = tmdbClient,
            tvListMapper = ListMapper.Impl(Mapper.TVResponseToDomain()),
            tvMapper = Mapper.TVDetailResponseToDomain(),
        )
        val movieLocalDataSource = MovieLocalDataSource.Default(
           mediaLionDb = MediaLionDatabaseFactory(
               driver = DatabaseDriverFactory(this)
           ).create(),
            movieDetailMapper = Mapper.MovieDetailEntityToDomain(),
            movieMapper = Mapper.MovieEntityToDomain(),
        )
        val movieRepository = MovieRepository.Default(
            movieRemoteDataSource,
            movieLocalDataSource,
            Mapper.MovieDetailDomainToEntity(),
            Mapper.MovieDomainToEntity(),
        )
        val strictMovieRepo = MovieRepository.Strict(movieRepository)
        val tvRepository = TVRepository.Default(
            tvRemoteDataSource,
            localDataSource = TVLocalDataSource.Default(
                mediaLionDb = MediaLionDatabaseFactory(
                    driver = DatabaseDriverFactory(this)
                ).create(),
                tvDetailMapper = Mapper.TVShowEntityToTVDetailDomain(),

            ), mapper = Mapper.TVShowDetailDomainToTVShowEntity()
        )

        globalServices = GlobalServices.builder()
            .add(this)
            .rebind<Context>(this)
            .add(tvRemoteDataSource)
            .rebind<TVRemoteDataSource>(tvRemoteDataSource)
            .add(movieRemoteDataSource)
            .rebind<MovieRemoteDataSource>(movieRemoteDataSource)
            .add(tmdbClient)
            .rebind<TMDBClient>(tmdbClient)
            .add(strictMovieRepo)
            .rebind<MovieRepository>(strictMovieRepo)
            .add(tvRepository)
            .rebind<TVRepository>(tvRepository)
            .build()
    }
}