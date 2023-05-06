//
//  CustomAboutDialog.swift
//  iosApp
//
//  Created by Nadine Cilliers on 30/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 15.0, *)
struct CustomAboutDialog: View {
    
    @Binding var isActive: Bool
    let title: String
    let message1: String
    let message2: String
    let message3: String
    let message4: String
    let message5: String
    let message6: String
    let action: () -> ()
    let buttonTitle: String
    @State private var offset: CGFloat = 1000
    
    var body: some View {
        
        
            VStack{
                Text(title)
                    .customFont(.subtitle1)
                    .padding()
                
                Text(message1)
                    .customFont(.h4)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.bottom, 5)
                
                Text(message2)
                    .customFont(.body2)
                    .lineSpacing(1)
                    .padding(.bottom, 1)
                
                Text(message3)
                    .customFont(.body2)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                
                Text(message4)
                    .customFont(.body2)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.bottom, 1 )
                
                Text(message5)
                    .customFont(.body2)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                Text(message6)
                    .customFont(.body2)
                    .frame(maxWidth: .infinity, alignment: .leading)
            
                
            }
           
            .padding()
            .padding(.bottom)
            .background(Color.dialogOrange)
            .clipShape(RoundedRectangle(cornerRadius: 10))
            .overlay {
                VStack{
                    HStack{
                        
                        Spacer()
                        
                        Button{
                            close()
                        }label: {
                            Image("closeIcon")
                                .resizable()
                                .frame(width: 30, height: 30)
                        }
                        
                    }
                    
                    Spacer()
                }
                
                .padding(.top, 25)
                .padding(.trailing, 20)
               
            }
            .shadow(radius: 10)
            .padding(30)
            .offset(x: 0, y: offset)
            .onAppear{
                withAnimation(.spring()) {
                    offset = 0
                }
            }
        }
    
        
        func close() {
            withAnimation(.spring()) {
                offset = 1000
                isActive = false
            }
        }
    }


@available(iOS 15.0, *)
struct CustomAboutDialog_Previews: PreviewProvider {
    static var previews: some View {
        CustomAboutDialog(isActive: .constant(true),title: "About", message1: "MediaLion Information", message2: "This App is a media organiser, which allows users to save and store their favorite movies, series and documentaries in custom collections.", message3: "App Developers:", message4: "Kai Cilliers & Nadine Cilliers", message5: "Graphic Designer:", message6: "Roxie Nemes", action: {}, buttonTitle: "Done")
    }
}
