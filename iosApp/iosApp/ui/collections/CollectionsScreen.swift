//
//  collectionScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CollectionsScreen: View {
    
    let onInfoClicked: () -> Void
    @StateObject var viewModel = CollectionViewModel()
    let onMediaItemClicked: (MediaItemUI) -> Void
    
    var body: some View {
        ZStack{
        VStack (alignment: .center, spacing: 0){
                HStack{
                    
                    NavigationLink(value: MLRootDestinations.search) {
                        Image("searchIcon")
                            .resizable()
                            .frame(width: 27, height: 30)
                    }
                    
                    Spacer()
                    
                    Button {
                        onInfoClicked()
                    } label: {
                        Image("aboutIcon")
                            .resizable()
                            .frame(width: 30, height: 30)
                    }
                    
                }
                .padding()
                .background(Color.background)
                
                
                ScrollView {
                    VStack (alignment: .center, spacing: 0){
                        switch(viewModel.state) {
                            
                        case let emptyState as CollectionState.Empty:
                            Text("empty")
                            
                        case let loadingState as CollectionState.Loading:
                            LoadingScreen()
                            
                        case let withCollectionsState as CollectionState.AllCollections:
                            ForEach(withCollectionsState.collections as [CollectionWithMediaUI], id: \.self) { c in
                                    MLTitledMediaRow(
                                        rowTitle: c.name as! String,
                                        media: c.contents,
                                        onMediaItemClicked: { mediaItem in
                                            onMediaItemClicked(mediaItem)
                                        }
                                    )
                            }
                            
                        default:
                            fatalError("unknown state for collections screen - \(viewModel.state)")
                        }
                    }
                }
            }
       
        }
        .background(Color.background)
        .onAppear {
            print("IOS - collection - starting to observe viewModel")
            viewModel.observe()
        }
        .onDisappear {
            print("IOS - collection - disposing viewModel")
            viewModel.dispose()
        }
        
    }
}

struct collectionScreen_Previews: PreviewProvider {
    static var previews: some View {
        CollectionsScreen(
            onInfoClicked: {},
            onMediaItemClicked: {_ in}
        )
    }
}
