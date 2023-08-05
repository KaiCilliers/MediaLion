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

struct MLDiscoveryFiltersRow: View {
    
    @State var selectedFilter : Filter = .all
    let onNewSelection: (Filter) -> ()
    
    
    var body: some View {
        ZStack{
            HStack {
                MLDiscoveryFilterItem(
                    filterName: StringRes.filterAll,
                    selected: selectedFilter == .all,
                    onClick: {
                        selectedFilter = .all
                        onNewSelection(selectedFilter)
                    }
                )
                
                Spacer()
                
                MLDiscoveryFilterItem(
                    filterName: StringRes.filterMovies,
                    selected: selectedFilter == .movies,
                    onClick: {
                        selectedFilter = .movies
                        onNewSelection(selectedFilter)
                    }
                )
                
                Spacer()
                
                MLDiscoveryFilterItem(
                    filterName: StringRes.filterSeries,
                    selected: selectedFilter == .series,
                    onClick: {
                        selectedFilter = .series
                        onNewSelection(selectedFilter)
                    }
                )
                
                Spacer()
                
                MLDiscoveryFilterItem(
                    filterName: StringRes.filterCategories,
                    selected: selectedFilter == .categories,
                    onClick: {
                        selectedFilter = .categories
                        onNewSelection(selectedFilter)
                    }
                )
                
            }
            .padding(.horizontal, 20)
            .padding(.vertical, 10)
            .background(Color.background)
        }
       
    }
}
    
    struct MLDiscoveryFilterRow: PreviewProvider {
        static var previews: some View {
            MLDiscoveryFiltersRow( onNewSelection: {_ in })
        }
}
