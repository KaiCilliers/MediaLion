package com.example.medialion.android.app

import android.app.Application
import com.example.medialion.data.HttpClientFactory
import com.example.medialion.data.clients.TMDBClient
import com.example.medialion.data.datasource.MovieRemoteDataSource
import com.example.medialion.data.datasource.TVRemoteDataSource
import com.example.medialion.data.repos.MovieRepository
import com.example.medialion.data.repos.TVRepository
import com.example.medialion.domain.mappers.ListMapper
import com.example.medialion.domain.mappers.Mapper
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
            tvListMapper = ListMapper.Impl(Mapper.TVResponseToDomain())
        )
        val movieRepository = MovieRepository.Default(movieRemoteDataSource)
        val strictMovieRepo = MovieRepository.Strict(movieRepository)
        val tvRepository = TVRepository.Default(tvRemoteDataSource)

        globalServices = GlobalServices.builder()
            .add(tmdbClient)
            .rebind<TMDBClient>(tmdbClient)
            .add(strictMovieRepo)
            .rebind<MovieRepository>(strictMovieRepo)
            .add(tvRepository)
            .rebind<TVRepository>(tvRepository)
            .build()
    }
}