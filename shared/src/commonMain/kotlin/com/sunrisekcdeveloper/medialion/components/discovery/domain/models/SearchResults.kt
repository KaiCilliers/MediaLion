package com.sunrisekcdeveloper.medialion.components.discovery.domain.models

import com.sunrisekcdeveloper.medialion.components.shared.domain.models.MediaWithTitle

interface SearchResults {
    fun results(): MediaWithTitle

    data class Default(
        private val results: MediaWithTitle
    ) : SearchResults {
        constructor() : this(
            results = MediaWithTitle.Def("default"),
        )

        override fun results(): MediaWithTitle {
            return results
        }
    }
}