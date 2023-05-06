//
//  AboutScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 15.0, *)
struct AboutScreen: View {
    
    @State var isActive: Bool = false

    var body: some View {
        ZStack{
            VStack{
                Button{
                    isActive = true
                } label: {
                    Image("aboutIcon").background(Color.background)
                }
            }
            .padding()
            
            if isActive {
                CustomAboutDialog(isActive: .constant(true), title: "About", message1: "MediaLion Information", message2: "This App is a media organiser, which allows users to save and store their favorite movies, series and documentaries in custom collections.", message3: "App Develeopers:", message4: "Kai Cilliers", message5: "Graphic Designer:", message6: "Roxie Nemes", action: {}, buttonTitle: "Done")
            }
        }
    }
}

@available(iOS 15.0, *)
struct AboutScreen_Previews: PreviewProvider {
    static var previews: some View {
        AboutScreen()
    }
}
