//
//  discoveryFilterItems.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

enum Filter: Int {
    case all = 0
    case movies = 1
    case series = 2
    case categories = 3 }

@available(iOS 15.0, *)
struct discoveryFilterItems: View {
    
    @State var selectedFilter : Filter = .all
    @State var isActive : Bool = false
    
    var body: some View {
        ZStack{
            HStack {
                Button {
                    selectedFilter = .all
                } label: {
                    
                    if selectedFilter == .all {
                        Text("All")
                            .customFont(.h3)
                            .foregroundColor(.mlLightBlue)
                    } else {
                        Text("All")
                            .customFont(.h3)
                        .foregroundColor(.dialogOrange) }
                }
                
                Spacer()
                
                Button {
                    
                    selectedFilter = .movies
                    
                } label: {
                    
                    if selectedFilter == .movies {
                        Text("Movies")
                            .customFont(.h3)
                            .foregroundColor(.mlLightBlue)
                    } else {
                        Text("Movies")
                            .customFont(.h3)
                        .foregroundColor(.dialogOrange) }
                }
                
                Spacer()
                
                Button {
                    
                    selectedFilter = .series
                    
                } label: {
                    
                    if selectedFilter == .series{
                        Text("Series")
                            .customFont(.h3)
                            .foregroundColor(.mlLightBlue)
                    } else {
                        Text("Series")
                            .customFont(.h3)
                        .foregroundColor(.dialogOrange) }
                }
                
                Spacer()
                
                Button {
                    
                    selectedFilter = .categories
                    isActive = true
                    
                } label: {
                    
                    
                    if selectedFilter == .categories {
                        Text("Categories")
                            .customFont(.h3)
                            .foregroundColor(.mlLightBlue)
                    } else {
                        Text("Categories")
                            .customFont(.h3)
                        .foregroundColor(.dialogOrange) }
                }
                
                
            }
            .padding(20)
            .background(Color.background)
            
         
            
        }
        
        
       
    }
}

@available(iOS 15.0, *)
struct discoveryFilterItems_Previews: PreviewProvider {
    static var previews: some View {
        discoveryFilterItems()
    }
}
