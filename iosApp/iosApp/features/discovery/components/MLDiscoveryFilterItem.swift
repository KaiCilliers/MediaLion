//
//  MLDiscoveryFilterItem.swift
//  iosApp
//
//  Created by Nadine Cilliers on 05/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLDiscoveryFilterItem : View {
    let filterName: String
    let selected: Bool
    let onClick: () -> Void
    
    var body: some View {
        if selected {
            Text(filterName)
                .customFont(.h3)
                .foregroundColor(.mlLightBlue)
                .onTapGesture { onClick() }
            
        } else {
            Text(filterName)
                .customFont(.h3)
                .foregroundColor(.dialogOrange)
                .onTapGesture { onClick() }
        }
    }
}

struct MLDiscoveryFilterItem_Previews: PreviewProvider {
    static var previews: some View {
        MLDiscoveryFilterItem(
            filterName: "Noice",
            selected: true,
            onClick: {}
        )
    }
}
