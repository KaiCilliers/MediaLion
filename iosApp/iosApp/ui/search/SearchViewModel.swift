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
    
    private let viewModel = WrapperMLSearchViewModel().instance()
    @Published var state: SearchState = SearchState.Idle(searchQuery: "", suggestedMedia: [])
    
    func submitAction(action: SearchAction) {
        self.viewModel.submitAction(action: action)
    }
    
    func observe() {
        handle = viewModel.state.subscribe(onCollect: { state in
            // essentially a null check
            if let state = state {
                self.state = state
            }
        })
    }
    
    func dispose() {
        handle?.dispose()
    }
    
}
