//
//  discoveryFilterItems.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

enum Filter: Int {
    case all = 0
    case movies = 1
    case series = 2
    case categories = 3 }

struct DiscoveryFilterItems: View {
    
    @State var selectedFilter : Filter = .all
    let action: (Filter) -> ()
    
    
    var body: some View {
        ZStack{
            HStack {
                Button {
                    selectedFilter = .all
                    action(selectedFilter)
                } label: {
                    
                    if selectedFilter == .all {
                        Text(StringRes.filterAll)
                            .customFont(.h3)
                            .foregroundColor(.mlLightBlue)
                    } else {
                        Text(StringRes.filterAll)
                            .customFont(.h3)
                        .foregroundColor(.dialogOrange) }
                }
                
                Spacer()
                
                Button {
                    
                    selectedFilter = .movies
                    action(selectedFilter)
                    
                } label: {
                    
                    if selectedFilter == .movies {
                        Text(StringRes.filterMovies)
                            .customFont(.h3)
                            .foregroundColor(.mlLightBlue)
                    } else {
                        Text(StringRes.filterMovies)
                            .customFont(.h3)
                        .foregroundColor(.dialogOrange) }
                }
                
                Spacer()
                
                Button {
                    
                    selectedFilter = .series
                    action(selectedFilter)
                    
                } label: {
                    
                    if selectedFilter == .series{
                        Text(StringRes.filterSeries)
                            .customFont(.h3)
                            .foregroundColor(.mlLightBlue)
                    } else {
                        Text(StringRes.filterSeries)
                            .customFont(.h3)
                        .foregroundColor(.dialogOrange) }
                }
                
                Spacer()
                
                Button {
                    
                    selectedFilter = .categories
                    action(selectedFilter)
                    
                    
                } label: {
                    
                    
                    if selectedFilter == .categories {
                        Text(StringRes.filterCategories)
                            .customFont(.h3)
                            .foregroundColor(.mlLightBlue)
                    } else {
                        Text(StringRes.filterCategories)
                            .customFont(.h3)
                        .foregroundColor(.dialogOrange) }
                }
                
                
            }
            .padding(.horizontal, 20)
            .padding(.vertical, 10)
            .background(Color.background)
            
         
            
        }
        
        
       
    }
}

struct discoveryFilterItems_Previews: PreviewProvider {
    static var previews: some View {
        DiscoveryFilterItems( action: {_ in })
    }
}
