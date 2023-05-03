package com.example.medialion.domain.components.search.usecases

import com.example.medialion.data.models.TVDetailResponse
import com.example.medialion.data.repos.MovieRepository
import com.example.medialion.data.repos.TVRepository
import com.example.medialion.domain.models.MovieDetail
import com.example.medialion.domain.models.TVShowDetail

interface MovieDetailsUseCase {
    suspend operator fun invoke(id: Int): MovieDetail
    class Default(
        private val movieRepo: MovieRepository
    ) : MovieDetailsUseCase {
        override suspend operator fun invoke(id: Int): MovieDetail = fooCatching("msg") {
            movieRepo.movieDetails(id)
        }
    }
}

public inline fun <R> fooCatching(msg: String, block: () -> R): R {
    return try {
        block()
    } catch (e: Throwable) {
        throw Exception("msg", e)
    }
}

public inline fun <T, R> T.fooCatching(msg: String, block: T.() -> R): R {
    return try {
        block()
    } catch (e: Throwable) {
        throw Exception("msg", e)
    }
}


interface TVDetailsUseCase {
    suspend operator fun invoke(id: Int): TVShowDetail
    class Default(
        private val tvRepo: TVRepository
    ) : TVDetailsUseCase {
        override suspend operator fun invoke(id: Int): TVShowDetail = fooCatching("msg") {
            tvRepo.tvDetails(id)
        }
    }
}