package com.sunrisekcdeveloper.medialion.oldArch.domain.search

import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.CreateCollectionUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.DeleteCollectionUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.FetchAllCollectionsUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.FetchAllGenresUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.FetchCollectionUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.RemoveMediaFromCollectionUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.RenameCollectionUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.SetupInitialCollectionUseCase
import com.sunrisekcdeveloper.medialion.oldArch.domain.search.usecases.SaveMediaToCollectionUseCase

interface CollectionComponent {

    val saveMediaToCollection: SaveMediaToCollectionUseCase
    val removeMediaFromCollection: RemoveMediaFromCollectionUseCase
    val createCollection: CreateCollectionUseCase
    val fetchAllCollections: FetchAllCollectionsUseCase
    val fetchCollection: FetchCollectionUseCase
    val renameCollection: RenameCollectionUseCase
    val fetchAllGenresUseCase: FetchAllGenresUseCase
    val deleteCollectionUseCase: DeleteCollectionUseCase
    val setupInitialCollections : SetupInitialCollectionUseCase

    class Default(
        override val saveMediaToCollection: SaveMediaToCollectionUseCase,
        override val removeMediaFromCollection: RemoveMediaFromCollectionUseCase,
        override val createCollection: CreateCollectionUseCase,
        override val fetchAllCollections: FetchAllCollectionsUseCase,
        override val fetchCollection: FetchCollectionUseCase,
        override val renameCollection: RenameCollectionUseCase,
        override val fetchAllGenresUseCase: FetchAllGenresUseCase,
        override val deleteCollectionUseCase: DeleteCollectionUseCase,
        override val setupInitialCollections: SetupInitialCollectionUseCase,
    ) : CollectionComponent

}