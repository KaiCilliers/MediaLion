//
//  SearchViewModel.swift
//  iosApp
//
//  Created by Nadine Cilliers on 06/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor class SearchViewModel: ObservableObject {
    
    private var handle: DisposableHandle?
    private var handleCollection: DisposableHandle?
    
    private var viewModel: MLSearchViewModel = WrapperMLSearchViewModel().instance()
    @Published var state: SearchState = SearchState.Idle(searchQuery: "", suggestedMedia: [])
    @Published var collectionState: NSArray = []
    
    func submitAction(action: SearchAction) {
        print("IOS - submitting action \(action)")
        self.viewModel.submitAction(action: action)
    }
    
    func observe() {
        handle = viewModel.state.subscribe(onCollect: { state in
            // essentially a null check
            if let state = state {
                print("IOS - got a state \(state)")
                self.state = state
            }
        })
        handleCollection = viewModel.allCollectionsState.subscribe(onCollect: { collections in
            if let collections = collections {
                self.collectionState = collections
            }
        })
    }
    
    func dispose() {
        print("IOS - SV - disposed viewmodel observations")
        handle?.dispose()
        handleCollection?.dispose()
    }
    
}
