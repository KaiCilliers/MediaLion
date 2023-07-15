package com.sunrisekcdeveloper.medialion.components.discovery.domain.models

sealed class MediaTypeNew {
    object All : MediaTypeNew()
    object Movie : MediaTypeNew()
    object TVShow : MediaTypeNew()
}