//
//  rootView.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 16.0, *)
struct rootView: View {
    
    @State var selectedTab : Tabs = .home
    var body: some View {
        VStack{
            Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/).foregroundColor(.white)
            
            Spacer()
            
            bottomBar(selectedTab: $selectedTab)
        }.background(Color.background)
    }
}

@available(iOS 16.0, *)
struct rootView_Previews: PreviewProvider {
    static var previews: some View {
        rootView()
    }
}
