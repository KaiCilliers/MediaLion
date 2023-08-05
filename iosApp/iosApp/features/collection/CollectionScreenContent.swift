//
//  CollectionScreenContent.swift
//  iosApp
//
//  Created by Nadine Cilliers on 05/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CollectionScreenContent: View {
    
    let collectionState: MyCollectionsUIState
    let openInfoDialog: () -> Void
    let openMediaPreviewSheet: (MediaItemUI) -> Void
    
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
                    
                    Button { openInfoDialog() } label: {
                        Image("aboutIcon")
                            .resizable()
                            .frame(width: 30, height: 30)
                    }
                    
                }
                .padding()
                .background(Color.background)
                
                
                ScrollView {
                    VStack (alignment: .center, spacing: 0){
                        switch(collectionState) {
                            
                        case let _ as FailedToFetchCollections:
                            MLSearchLoading()
                            
                        case let _ as Loading_:
                            MLSearchLoading()
                            
                        case let contentState as MyCollectionsContent:
                            
                            let iosCollections = contentState.collections.map { collection in
                                CollectionsNewIOS(origin: collection)
                            }
                            
                            ForEach(iosCollections) { c in
                                
                                let media = c.origin.media().map { domainItem in
                                    MediaItemUI.companion.from(domain: domainItem)
                                }
                                
                                MLTitledMediaRow(
                                    rowTitle: "\(c.origin.title())",
                                    media: media,
                                    onMediaItemClicked: { openMediaPreviewSheet($0) },
                                    onTitleClicked: {}
                                )
                            }
                        default:
                            fatalError("unknown state for collections screen - \(collectionState)")
                        }
                    }
                }
            }.padding(.bottom, 30)
            
        }
        .background(Color.background)
    }
}

struct CollectionScreenContent_Previews: PreviewProvider {
    static var previews: some View {
        CollectionScreenContent(
            collectionState: MyCollectionsContent(
                id: IDDef(),
                collections: [
                    CollectionNewDef(name: "Collection #1", mediaItem: SingleMediaItemMovie(name: "Movie")),
                    CollectionNewDef(name: "Collection #2", mediaItem: SingleMediaItemMovie(name: "Movie")),
                    CollectionNewDef(name: "Collection #3", mediaItem: SingleMediaItemMovie(name: "Movie")),
                ]
            ),
            openInfoDialog: {},
            openMediaPreviewSheet: {_ in}
        )
    }
}
