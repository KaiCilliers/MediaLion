package com.example.medialion.domain.components.search

import com.example.medialion.domain.components.search.usecases.DocumentariesRelatedToUseCase
import com.example.medialion.domain.components.search.usecases.MovieDetailsUseCase
import com.example.medialion.domain.components.search.usecases.MoviesRelatedToUseCase
import com.example.medialion.domain.components.search.usecases.SuggestedMediaUseCase
import com.example.medialion.domain.components.search.usecases.TVDetailsUseCase
import com.example.medialion.domain.components.search.usecases.TVRelatedToUseCase
import com.example.medialion.domain.components.search.usecases.TopMediaResultsUseCase
import com.example.medialion.domain.models.MediaItem
import com.example.medialion.domain.models.Movie
import com.example.medialion.domain.models.MovieDetail
import com.example.medialion.domain.models.TVShow
import com.example.medialion.domain.models.TVShowDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take

interface SearchComponent {

    suspend fun detailsForMovie(id: Int): MovieDetail
    suspend fun detailsForTV(id: Int): TVShowDetail
    fun relatedDocumentaries(id: Int): Flow<TVShow>
    fun relatedMovies(id: Int): Flow<Movie>
    fun suggestedMedia(): Flow<Movie>
    fun relatedTVShows(id: Int): Flow<TVShow>
    fun searchResults(query: String): Flow<MediaItem>

    class Default(
        private val movieDetails: MovieDetailsUseCase,
        private val tvDetails: TVDetailsUseCase,
        private val relatedDocumentariesUseCase: DocumentariesRelatedToUseCase,
        private val relatedMoviesUseCase: MoviesRelatedToUseCase,
        private val suggestedMediaUseCase: SuggestedMediaUseCase,
        private val topMediaResultsUseCase: TopMediaResultsUseCase,
        private val tvRelatedToUseCase: TVRelatedToUseCase,
    ) : SearchComponent {
        override suspend fun detailsForMovie(id: Int): MovieDetail {
            return movieDetails(id)
        }

        override suspend fun detailsForTV(id: Int): TVShowDetail {
            return tvDetails(id)
        }

        override fun relatedDocumentaries(id: Int): Flow<TVShow> = flow {
            emitAll(relatedDocumentariesUseCase(id))
        }
            .take(21)

        override fun relatedMovies(id: Int): Flow<Movie> = flow {
            emitAll(relatedMoviesUseCase(id))
        }
            .take(21)

        override fun suggestedMedia(): Flow<Movie> = flow {
            emitAll(suggestedMediaUseCase())
        }
            .take(21)

        override fun relatedTVShows(id: Int): Flow<TVShow> = flow {
            emitAll(tvRelatedToUseCase(id))
        }
            .take(21)

        override fun searchResults(query: String): Flow<MediaItem> = flow {
            emitAll(topMediaResultsUseCase(query))
        }
            .take(21)
    }
}