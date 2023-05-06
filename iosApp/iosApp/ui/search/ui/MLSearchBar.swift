//
//  MLSearchBar.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLSearchBar: View {
    @Binding var text: String
    
    
    
    var body: some View {
        
        HStack{
            Image("searchIcon")
                .resizable()
                .frame(width: 30, height: 35)
                .padding(20)
            TextField(
                "", text: $text
            ).foregroundColor(.white).customFont(.h1)
            .modifier(
                PlaceholderStyle(
                    showPlaceHolder: text.isEmpty,
                    placeholder: "Search")
            )
            if !text.isEmpty {
                      Button(
                          action: { self.text = "" },
                          label: {
                              Image("cancelText")
                                  .resizable()
                                  .frame(width: 30, height: 30)
                                  .padding(20)
                               
                          }
                      )
                  }
           
            
           

        }
        .background(LinearGradient(gradient: Gradient(colors: [.primaryVariant, .mlPrimary]), startPoint: .topTrailing, endPoint: .bottomLeading))
           
            
               
        
      
        
       
    }
}

struct MLSearchBar_Previews: PreviewProvider {
    static var previews: some View {
        StatefulPreviewWrapper("The Office") {
            MLSearchBar(text: $0)
        }
    }
}


public struct PlaceholderStyle: ViewModifier {
    var showPlaceHolder: Bool
    var placeholder: String

    public func body(content: Content) -> some View {
        ZStack(alignment: .leading) {
            if showPlaceHolder {
                Text(placeholder)
                    .foregroundColor(Color.white)
                    .opacity(0.7)
            }
            content
        }
    }
}
