//
//  CustomAboutDialog.swift
//  iosApp
//
//  Created by Nadine Cilliers on 30/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MLAboutDialog: View {
    
    @State private var offset: CGFloat = 1000
    let onCloseAction: () -> Void
    
    var body: some View {
        
            VStack{
                Text(StringRes.about)
                    .customFont(.subtitle1)
                    .padding()
                
                Text(StringRes.aboutHeading)
                    .customFont(.h4)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.bottom, 5)
                
                Text(StringRes.appDescription)
                    .customFont(.body2)
                    .lineSpacing(1)
                    .padding(.bottom, 1)
                
                Text(StringRes.appDevelopers)
                    .customFont(.body2)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                
                Text(StringRes.appDevelopersNames)
                    .customFont(.body2)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.bottom, 1 )
                
                Text(StringRes.graphicDesigner)
                    .customFont(.body2)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                Text(StringRes.graphicDesignerName)
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
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
                onCloseAction()
            }
        }
    }


struct CustomAboutDialog_Previews: PreviewProvider {
    static var previews: some View {
        MLAboutDialog(onCloseAction: {})
    }
}


private struct AboutScreen: View {
    
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

struct AboutScreen_Previews: PreviewProvider {
    static var previews: some View {
        AboutScreen()
    }
}
