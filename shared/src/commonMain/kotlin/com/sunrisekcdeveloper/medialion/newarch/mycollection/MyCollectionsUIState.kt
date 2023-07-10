package com.sunrisekcdeveloper.medialion.newarch.mycollection

sealed interface MyCollectionsUIState
object FailedToFetchCollections : MyCollectionsUIState
object Loading : MyCollectionsUIState
object MyCollectionsContent : MyCollectionsUIState