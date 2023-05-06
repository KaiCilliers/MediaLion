//
//  bottomBar.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

enum Tabs: Int {
    case home = 0
    case collection = 1
}

@available(iOS 16.0, *)
struct bottomBar: View {
    
    @Binding var selectedTab : Tabs
    
    var body: some View {
        HStack{
            
                       
            Button {
                
                selectedTab = .home
                
            } label: {
                
                GeometryReader { geo in
                    
                    if selectedTab == .home {
                        Rectangle()
                            .foregroundColor(.dialogOrange)
                            .frame(width: geo.size.width/2, height: 6)
                        .padding(.leading, geo.size.width/4)
                        
                    }
                    
                    VStack (alignment: .center, spacing: 4){
                        Image("homeIcon")
                            .resizable()
                            .scaledToFit()
                            .frame(width: 20, height: 24)
                           
                        Text("Home")
                            .customFont(.h1)
                    }
                    .frame(width: geo.size.width, height: geo.size.height)
                }
            }
            .tint(Color.primaryVariant)
            
         
            
            Button {
                
                selectedTab = .collection
                
            } label: {
                
                GeometryReader { geo in
                    
                    if selectedTab == .collection {
                        Rectangle()
                            .foregroundColor(.dialogOrange)
                            .frame(width: geo.size.width/2, height: 6)
                            .padding(.leading, geo.size.width/4)
                    }
                    
                    VStack (alignment: .center, spacing: 4){
                        
                        Image("slideOrangeCogIcon")
                            .resizable()
                            .scaledToFit()
                            .frame(width: 20, height: 24)
                        
                        Text("My Collection")
                            .customFont(.h1)
                    }
                    .frame(width: geo.size.width, height: geo.size.height)
                }
            }
            .tint(Color.primaryVariant)
            
         
            
        }
        .frame(height: 82)
        .background(LinearGradient(gradient: Gradient(colors: [.mlPrimary, .primaryVariantBlue]), startPoint: .top, endPoint: .bottom))
        .edgesIgnoringSafeArea(.all)
    }
}

@available(iOS 16.0, *)
struct bottomBar_Previews: PreviewProvider {
    static var previews: some View {
        bottomBar(selectedTab: .constant(.home))
            
    }
}
