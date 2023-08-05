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
    
    private var sharedDiscoveryViewModel: MLDiscoveryViewModelNew = WrappedMLDiscoveryViewModelNew().instance()
    private var sharedMiniCollectionViewModel: MLMiniCollectionViewModel = WrappedMLMiniCollectionViewModel().instance()
    private var sharedCategoriesViewModel: MLCategoriesViewModel = WrappedMLCategoriesViewModel().instance()
    
    @Published var discoveryState: DiscoveryUIState = DiscoveryUIState.Loading(tabSelection: DiscoveryScreenTabSelection.All())
    @Published var miniCollectionState: MiniCollectionUIState = Loading()
    @Published var categoriesState: CategoriesUIState = CategoriesUIStateLoading()
    
    func submitAction(action: DiscoveryNewActions) {
        print("IOS - submitting action \(action)")
        self.sharedDiscoveryViewModel.submit(action____: action)
    }
    
    func submitAction(action: CategoriesAction) {
        print("IOS - submitting action \(action)")
        self.sharedCategoriesViewModel.submit(action___: action)
    }
    
    func submitAction(action: MiniCollectionAction) {
        print("IOS - submitting action \(action)")
        self.sharedMiniCollectionViewModel.submit(action__: action)
    }
    
    func observe() {
        handle = sharedDiscoveryViewModel.discState.subscribe(onCollect: { state in
            if let state = state {
                print("IOS - discovery - state \(state)")
                self.discoveryState = state
            }
        })
        collectionHandle = sharedMiniCollectionViewModel.miniCollectionState.subscribe( onCollect: { state in
            if let state = state {
                print("IOS - discovery - mini collection state \(state)")
                self.miniCollectionState = state
            }
        })
        genreHandle = sharedCategoriesViewModel.catState.subscribe(onCollect: { state in
            if let state = state {
                print("IOS - discovery - categories state \(state)")
                self.categoriesState = state
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
