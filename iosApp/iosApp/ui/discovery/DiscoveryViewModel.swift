//
//  DiscoveryViewModel.swift
//  iosApp
//
//  Created by Nadine Cilliers on 20/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor class DiscoveryViewModel: ObservableObject {
    
    private var handle: DisposableHandle?
    private var collectionHandle: DisposableHandle?
    private var genreHandle: DisposableHandle?
    
    private var viewModel: MLDiscoveryViewModel = WrapperMLDiscoveryViewModel().instance()
    private var collectionViewModel: MLCollectionViewModel = WrapperMLCollectionViewModel().instance()
    
    @Published var state: DiscoveryState = DiscoveryState.Loading()
    @Published var collectionState: NSArray = []
    @Published var genreState: GenreState = GenreState.Genres(all: [])
    
    func submitAction(action: DiscoveryAction) {
        print("IOS - submitting action \(action)")
        self.viewModel.submitAction(action: action)
    }
    
    func observe() {
        handle = viewModel.state.subscribe(onCollect: { state in
            if let state = state {
                print("IOS - discovery - state \(state)")
                self.state = state
            }
        })
        collectionHandle = viewModel.allCollectionsState.subscribe( onCollect: { collections in
            if let collections = collections {
                self.collectionState = collections
            }
        })
        genreHandle = collectionViewModel.genres.subscribe(onCollect: { state in
            if let state = state {
                self.genreState = state
            }
        })
    }
    
    func dispose() {
        print("IOS - discovery - disposing observers")
        handle?.dispose()
        collectionHandle?.dispose()
        genreHandle?.dispose()
    }
}
