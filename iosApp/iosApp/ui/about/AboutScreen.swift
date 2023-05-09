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
                MLAboutDialog(onCloseAction: {})
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
