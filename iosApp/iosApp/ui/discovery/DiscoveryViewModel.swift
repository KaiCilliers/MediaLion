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
    
    private var viewModel: MLDiscoveryViewModel = WrapperMLDiscoveryViewModel().instance()
    
    @Published var state: DiscoveryState = DiscoveryState.Loading()
    
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
    }
    
    func dispose() {
        print("IOS - discovery - disposing observers")
        handle?.dispose()
    }
}
