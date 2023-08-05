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
    
    private var searchHandle: DisposableHandle?
    private var handleCollection: DisposableHandle?
    
    private let sharedSearchViewModel: MLSearchViewModelNew = WrappedMLSearchViewModelNew().instance()
    private let sharedMiniCollectionViewModel: MLMiniCollectionViewModelD = WrappedMLMiniCollectionViewModel().instance()
    
    @Published var searchState: SearchUIState = SearchUIState.TopSuggestions(searchQuery: SearchQueryDefault(value: ""), media: [])
    @Published var miniCollectionState: MiniCollectionUIState = Loading()
    
    func submitAction(action: SearchScreenAction) {
        print("IOS - submitting action \(action)")
        self.sharedSearchViewModel.submit(action: action)
    }
    
    func submitAction(action: MiniCollectionAction) {
        print("IOS - submitting action \(action)")
        self.sharedMiniCollectionViewModel.submit(action__: action)
    }
    
    func observe() {
        searchHandle = sharedSearchViewModel.searchState.subscribe(onCollect: { state in
            // essentially a null check
            if let state = state {
                print("IOS - got a state \(state)")
                self.searchState = state
            }
        })
        handleCollection = sharedMiniCollectionViewModel.miniCollectionState.subscribe(onCollect: { collections in
            if let collections = collections {
                self.miniCollectionState = collections
            }
        })
    }
    
    func dispose() {
        print("IOS - SV - disposed viewmodel observations")
        searchHandle?.dispose()
        handleCollection?.dispose()
    }
    
}
