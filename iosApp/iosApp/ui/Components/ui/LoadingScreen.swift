//
//  LoadingScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 10/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct LoadingScreen: View {
    var body: some View {
        ZStack{
            ProgressView("Searching for media...").customFont(.subtitle1).foregroundColor(.dialogOrange)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.background)
        
    }
}

struct LoadingScreen_Previews: PreviewProvider {
    static var previews: some View {
        LoadingScreen()
    }
}
