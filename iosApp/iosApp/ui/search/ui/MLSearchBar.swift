//
//  MLSearchBar.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLSearchBar: View {
    let text: String
    let labelText: String
    let onSearchQueryTextChanged: (String) -> Void
    let onClearSearchText: () -> Void
    
    @State private var textToShow: String
    
    init(text: String, labelText: String, onSearchQueryTextChanged: @escaping (String) -> Void, onClearSearchText: @escaping () -> Void) {
        self.text = text
        self.labelText = labelText
        self.onSearchQueryTextChanged = onSearchQueryTextChanged
        self.onClearSearchText = onClearSearchText
        self.textToShow = text
    }
    
    var body: some View {
        
        HStack{
            
            Image("searchIcon")
                .resizable()
                .frame(width: 30, height: 35)
                .padding(20)
            
            TextField("", text: $textToShow)
            .onChange(of: textToShow) { newValue in
                onSearchQueryTextChanged(newValue)
                
            }
            .foregroundColor(.white).customFont(.h1)
            .modifier(
                PlaceholderStyle(
                    showPlaceHolder: textToShow.isEmpty,
                    placeholder: labelText)
            )
            
            if !textToShow.isEmpty {
                      Button(
                          action: {
                              onClearSearchText()
                              self.textToShow = ""
                          },
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
        MLSearchBar(
            text: "",
            labelText: "Search for a show, movie or documentary",
            onSearchQueryTextChanged: {_ in},
            onClearSearchText: {}
        )
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
