package com.sunrisekcdeveloper.medialion.oldArch.domain.search

import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.DocumentariesRelatedToUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.MovieDetailsUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.MoviesRelatedToUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.SuggestedMediaUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.TVDetailsUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.TVRelatedToUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.TopMediaResultsUseCase

interface SearchComponent {

    val movieDetails: MovieDetailsUseCase
    val tvDetails: TVDetailsUseCase
    val relatedDocumentariesUseCase: DocumentariesRelatedToUseCase
    val relatedMoviesUseCase: MoviesRelatedToUseCase
    val suggestedMediaUseCase: SuggestedMediaUseCase
    val topMediaResultsUseCase: TopMediaResultsUseCase
    val tvRelatedToUseCase: TVRelatedToUseCase

    class Default(
        override val movieDetails: MovieDetailsUseCase,
        override val tvDetails: TVDetailsUseCase,
        override val relatedDocumentariesUseCase: DocumentariesRelatedToUseCase,
        override val relatedMoviesUseCase: MoviesRelatedToUseCase,
        override val suggestedMediaUseCase: SuggestedMediaUseCase,
        override val topMediaResultsUseCase: TopMediaResultsUseCase,
        override val tvRelatedToUseCase: TVRelatedToUseCase,
    ) : SearchComponent
}
