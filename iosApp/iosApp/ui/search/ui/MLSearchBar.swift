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
    
    @State private var isEditing = false
    
    var body: some View {
        HStack{
            TextField("Search", text: $text)
                .padding(7)
                .padding(.horizontal, 25)
                .background(Color.background)
                .onTapGesture {
                    self.isEditing = true
                }
                .foregroundColor(.white)
            if isEditing {
                Button(action: {
                    self.isEditing = false
                    self.text = ""
                }){
                    Text("Cancel")
                        .foregroundColor(.red)
                }
            }
           
        }
        .padding(.trailing, 10)
        .transition(.move(edge: .trailing))
        .animation(.default)
    }
}

struct MLSearchBar_Previews: PreviewProvider {
    static var previews: some View {
        MLSearchBar(text: .constant(""))
    }
}
