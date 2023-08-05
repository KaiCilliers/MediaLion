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
    
    var selectedFilter : Filter
    let onNewSelection: (Filter) -> ()
    
    
    var body: some View {
        ZStack{
            HStack {
                MLDiscoveryFilterItem(
                    filterName: StringRes.filterAll,
                    selected: selectedFilter == .all,
                    onClick: {
                        onNewSelection(selectedFilter)
                    }
                )
                
                Spacer()
                
                MLDiscoveryFilterItem(
                    filterName: StringRes.filterMovies,
                    selected: selectedFilter == .movies,
                    onClick: {
                        onNewSelection(selectedFilter)
                    }
                )
                
                Spacer()
                
                MLDiscoveryFilterItem(
                    filterName: StringRes.filterSeries,
                    selected: selectedFilter == .series,
                    onClick: {
                        onNewSelection(selectedFilter)
                    }
                )
                
                Spacer()
                
                MLDiscoveryFilterItem(
                    filterName: StringRes.filterCategories,
                    selected: selectedFilter == .categories,
                    onClick: {
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
            MLDiscoveryFiltersRow( selectedFilter: Filter.all, onNewSelection: {_ in })
        }
}
