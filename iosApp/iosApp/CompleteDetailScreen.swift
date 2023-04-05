//
//  CompleteDetailScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 05/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct CompleteDetailScreen: View {
    let item: String
    
    var body: some View {
        VStack {
            Text("More details about \(item)")
        }
    }
}

struct CompleteDetailScreen_Previews: PreviewProvider {
    static var previews: some View {
        CompleteDetailScreen(item: "Example")
    }
}
