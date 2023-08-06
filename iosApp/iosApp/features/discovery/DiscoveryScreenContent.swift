//
//  DiscoveryScreenContent.swift
//  iosApp
//
//  Created by Nadine Cilliers on 05/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct DiscoveryScreenContent: View {
    
    let discoveryState: DiscoveryUIState
    let fetchContentForPage: (DiscoveryPage) -> Void
    let showCategoryDialog: () -> Void
    let showInfoDialog: () -> Void
    let showMediaPreview: (MediaItemUI) -> Void
    
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
                    Image("logoIcon")
                        .resizable()
                        .frame(width: 100, height: 80)
                    Spacer()
                    
                    Button {
                        showInfoDialog()
                    } label: {
                        Image("aboutIcon")
                            .resizable()
                            .frame(width: 30, height: 30)
                    }
                    
                }
                .padding()
                .background(Color.background)
                
                let currentFilter: Filter = {
                    switch (discoveryState.tabSelection) {
                        case DiscoveryScreenTabSelection.All():
                            return .all
                        case DiscoveryScreenTabSelection.Categories():
                            return .categories
                        case DiscoveryScreenTabSelection.Movies():
                            return .movies
                        case DiscoveryScreenTabSelection.TVShows():
                            return .series
                    default:
                        return .all
                    }
                }()
                
                MLDiscoveryFiltersRow(
                    selectedFilter: currentFilter,
                    onNewSelection: { newFilter in
                        switch (newFilter){
                        case .all:
                            fetchContentForPage(DiscoveryPage.All())
                        case .movies:
                            fetchContentForPage(DiscoveryPage.Movies())
                        case .series:
                            fetchContentForPage(DiscoveryPage.TVShows())
                        case .categories:
                            showCategoryDialog()
                        }
                    }
                )
               
                ScrollView {
                    VStack (alignment: .center, spacing: 0){
                        switch(discoveryState) {
                            
                        case _ as DiscoveryUIState.Loading:
                            MLProgressIndicator(
                                loadingText: "Fetching media..."
                            )
                            
                        case _ as DiscoveryUIState.Error:
                            Text("Error...")
                                .foregroundColor(Color.white)
                            
                        case let contentState as DiscoveryUIState.Content:
                            if (contentState.media.all().count > 1) {
                                
                                let wrappedMediaWithTitle = contentState.media.all().map { sharedModel in
                                    MediaWithTitleIOS(origin: sharedModel as! MediaWithTitleDef)
                                }
                                
                                ForEach(wrappedMediaWithTitle) { item in
                                    MLTitledMediaRow(
                                        rowTitle: "\(item.origin.title())",
                                        media: item.origin.media().map({ domainItem in
                                            MediaItemUI.companion.from(domain: domainItem)
                                        }),
                                        onMediaItemClicked: { showMediaPreview($0) }
                                    )
                                }
                            } else {
                                
                                let mediaWithTitle = contentState.media.all().first!
                                
                                Text("\(mediaWithTitle.title())")
                                    .foregroundColor(.white)
                                    .customFont(.h2)
                                    .padding(.leading, 16)
                                    .padding(.top, 16)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                
                                LazyVGrid(
                                   columns: [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())],
                                   spacing: 16
                               ) {
                                   ForEach(mediaWithTitle.media().map({ domainItem in
                                       MediaItemUI.companion.from(domain: domainItem)
                                   }), id: \.self) { mediaItem in
                                       MLMediaPoster(
                                           title: mediaItem.title,
                                           posterUrl: mediaItem.posterUrl
                                       )
                                           .frame(width: 100,height: 170)
                                           .onTapGesture {
                                               showMediaPreview(mediaItem)
                                           }
                                   }

                               }
                            }
                        default:
                            fatalError("state should not be reachable \(discoveryState)")
                        }
                   
                    }
            }.padding(.bottom, 30)
            }
            .background(Color.background)
        }
    }
}

struct DiscoveryScreenContent_Previews: PreviewProvider {
    static var previews: some View {
        
        let state: DiscoveryUIState = DiscoveryUIState.Content(
            media: TitledMediaListDef(
                singleItem: MediaWithTitleDef(
                    title: Title(value: "Title #1"),
                    content: [
                        SingleMediaItemMovie(name: "Movie #1"),
                        SingleMediaItemMovie(name: "Movie #2"),
                        SingleMediaItemMovie(name: "Movie #3"),
                        SingleMediaItemMovie(name: "Movie #4")
                    ]
                )
            ),
            tabSelection: DiscoveryScreenTabSelection.Categories()
        )
        
        DiscoveryScreenContent(
            discoveryState: state,
            fetchContentForPage: {_ in},
            showCategoryDialog: {},
            showInfoDialog: {},
            showMediaPreview: {_ in}
        )
    }
}
