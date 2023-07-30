package com.sunrisekcdeveloper.medialion.components.discovery.domain.models

sealed class DiscoveryPage {
    object All : DiscoveryPage()
    object Movies : DiscoveryPage()
    object TVShows : DiscoveryPage()
}