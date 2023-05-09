//
//  discoveryScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct DiscoveryScreen: View {
    
    @State var isActive : Bool = false
    let onInfoClicked: () -> Void
    
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
//                        MLTitledMediaRow()
//                        MLTitledMediaRow()
//                        MLTitledMediaRow()
//                        MLTitledMediaRow()
//                        MLTitledMediaRow()
//                        MLTitledMediaRow()
                    }
                }
                
            }
            .background(Color.background)
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
