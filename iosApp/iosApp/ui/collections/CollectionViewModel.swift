////
////  CollectionViewModel.swift
////  iosApp
////
////  Created by Nadine Cilliers on 10/05/2023.
////  Copyright © 2023 orgName. All rights reserved.
////
//
//import Foundation
//import shared
//
//@MainActor class CollectionViewModel: ObservableObject {
//    
//    private var handle: DisposableHandle?
//    
//    private var viewModel: MLCollectionViewModel = WrapperMLCollectionViewModel().instance()
//    
//    @Published var state: CollectionState = CollectionState.Empty()
//    
//    func submitAction(action: CollectionAction) {
//        viewModel.submitAction(action: action)
//    }
//    
//    func observe() {
//        handle = viewModel.state.subscribe(onCollect: { state in
//            if let state = state {
//                print("IOS - collection - state \(state)")
//                self.state = state
//            }
//        })
//    }
//    
//    func dispose() {
//        print("IOS - collection - disposing observers")
//        handle?.dispose()
//    }
//    
//}
