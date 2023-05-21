//
//  discoveryScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct DiscoveryScreen: View {
    
    @State var isActive : Bool = false
    let onInfoClicked: () -> Void
    
    @StateObject private var viewModel = DiscoveryViewModel()
    
    var body: some View {
        var blurAmount: Float = {
            if isActive {
                return 4
            } else {
                return 0
            }
        }()
        
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
                        onInfoClicked()
                    } label: {
                        Image("aboutIcon")
                            .resizable()
                            .frame(width: 30, height: 30)
                    }
                    
                }
                .padding()
                .background(Color.background)
                
                DiscoveryFilterItems( action: {filter in
                    if filter == .categories {
                        isActive = true
                    }
                    
                }
                )
                
                ScrollView {
                    VStack (alignment: .center, spacing: 0){
                        switch(viewModel.state) {
                            
                        case let loadingState as DiscoveryState.Loading:
                            LoadingScreen()
                        case let errorState as DiscoveryState.Error:
                            Text("Error...")
                                .foregroundColor(Color.white)
                            
                        case let contentState as DiscoveryState.Content:
                            ForEach(contentState.media, id: \.self) { item in
                                MLTitledMediaRow(
                                    rowTitle: item.title,
                                    media: item.content) { selectedItem in
                                        print("item clicked - \(selectedItem.title)")
                                    }
                            }
                            
                            
                        default:
                            Text("default...")
                        }
                    }
                }
                
            }
            .background(Color.background)
        }
        .onAppear {
            print("IOS - discovery - starting to observe viewmodel")
            viewModel.observe()
            viewModel.submitAction(action: DiscoveryAction.FetchContent())
        }
        .onDisappear {
            print("IOS - discovery - disposing viewmodel")
            viewModel.dispose()
        }
    }
}

struct discoveryScreen_Previews: PreviewProvider {
    static var previews: some View {
        DiscoveryScreen(
            onInfoClicked: {}
        )
    }
}
