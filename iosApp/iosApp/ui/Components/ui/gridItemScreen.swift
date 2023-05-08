//
//  gridItemScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 16.0, *)
struct gridItemScreen: View {
    @State var selectedTab : Tabs = .home
    @Environment(\.editMode) var editMode
    
    var body: some View {
        ZStack{
            VStack (alignment: .center, spacing: 0) {
                HStack{
                    Image("backArrowIcon")
                        .resizable()
                        .frame(width: 30, height: 30)
                        .padding()
                    Spacer()
                    Image("aboutIcon")
                        .resizable()
                        .frame(width: 30, height: 30)
                        .padding()
                }.background(Color.background)
//                MLTitledMediaGrid(title: "Horror")
                
                bottomBar(selectedTab: $selectedTab)
            }
            .overlay {
                VStack{
                    Spacer()
                    HStack{
                        Spacer()
                        Button {
                            EditButton()
                        }label: {
                            Image("editIcon")
                                .resizable()
                                .frame(width: 90, height: 90)
                        }
                    }
                }
                .padding(.bottom, 100)
                .padding(.trailing, 20)
            }
        }
    }
}

@available(iOS 16.0, *)
struct gridItemScreen_Previews: PreviewProvider {
    static var previews: some View {
        gridItemScreen()
    }
}
