//
//  CollectionViewModel.swift
//  iosApp
//
//  Created by Nadine Cilliers on 10/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor class CollectionViewModel: ObservableObject {
    
    private var collectionHandle: DisposableHandle?
    private var miniCollectionHandle: DisposableHandle?
    
    private var sharedCollectionViewModel: MLMyCollectionViewModelNew = WrappedMLCollectionViewModelNew().instance()
    private var sharedMiniCollectionViewModel: MLMiniCollectionViewModel = WrappedMLMiniCollectionViewModel().instance()
    
    @Published var collectionState: MyCollectionsUIState = Loading_()
    @Published var miniCollectionState: MiniCollectionUIState = Loading()
    
    func submitAction(action: MyCollectionsAction) {
        sharedCollectionViewModel.submit(action_: action)
    }
    
    func submitAction(action: MiniCollectionAction) {
        sharedMiniCollectionViewModel.submit(action__: action)
    }
    
    func observe() {
        collectionHandle = sharedCollectionViewModel.collectionState.subscribe(onCollect: { state in
            if let state = state {
                print("IOS - collection - state \(state)")
                self.collectionState = state
            }
        })
        miniCollectionHandle = sharedMiniCollectionViewModel.miniCollectionState.subscribe(onCollect: { state in
            if let state = state {
                print("IOS - mini collection - state \(state)")
                self.miniCollectionState = state
            }
        })
    }
    
    func dispose() {
        print("IOS - collection - disposing observers")
        collectionHandle?.dispose()
        miniCollectionHandle?.dispose()
    }
    
}
