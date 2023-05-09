//
//  AboutScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

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
                CustomAboutDialog(isActive: .constant(true), title: StringRes.about, message1: StringRes.aboutHeading, message2: StringRes.appDescription, message3: StringRes.appDevelopers, message4: StringRes.appDevelopersNames, message5: StringRes.graphicDesigner, message6: StringRes.graphicDesignerName, action: {}, buttonTitle: "Done")
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
