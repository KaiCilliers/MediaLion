//
//  SearchEmptyState.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SearchEmptyState: View {
    var body: some View {
        VStack{
            Text("We could not find what you are looking for.")
            Text("Try searching for another movie, show, actor, director, or genre")
            
        }
        
        .foregroundColor(.white)
        .background(Color.background)
        
    }
}

struct SearchEmptyState_Previews: PreviewProvider {
    static var previews: some View {
        SearchEmptyState()
    }
}
