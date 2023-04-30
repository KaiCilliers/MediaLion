//
//  CustomCollectionDialog.swift
//  iosApp
//
//  Created by Nadine Cilliers on 30/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 15.0, *)
@available(iOS 15.0, *)
@available(iOS 15.0, *)
struct CustomCollectionDialog: View {
    
    @Binding var isActive: Bool
    let title: String
    let action: () -> ()
    let buttoTitle: String
    @State private var offset: CGFloat = 1000
    @State private var text: String = ""
    
    var body: some View {
        VStack{
            Text(title)
                .customFont(.subtitle1)
                .padding()
            
            
            ScrollView {
                VStack(spacing: 15) {
                    ForEach(0..<10) {_ in
                       MLCollectionListItem()
                            .background(Color.dialogOrange)
                    }
                }
            }.frame(height: 150)
            
            ZStack{
                HStack{
                    TextField("", text: $text)
                        .customFont(.h1)
                        .modifier(PlaceholderStyleCollections(showPlaceHolder: text.isEmpty, placeholder: "Create new collection")
                        )
                        .padding(.vertical)
                        .padding(.horizontal)
                    if !text.isEmpty {
                        Button(
                            action: { self.text = ""},
                            label: {
                                Image("nameCreated")
                                    .resizable()
                                    .frame(width: 20, height: 20)
                                    .padding()
                            }
                        )
                    }
                }
                    
            }.background(Color.newListTextField)
                .padding(.horizontal)
                .padding(.top)
            
            
            Button{
                close()
            } label: {
                ZStack{
                    RoundedRectangle(cornerRadius: 10)
                        .foregroundColor(.mlPrimary)
                    Text(buttoTitle)
                        .foregroundColor(.white)
                        .padding()
                }
                .padding(.horizontal, 100)
                .padding(.vertical)
            }
            
        }
        .fixedSize(horizontal: false, vertical: true)
        .padding()
        .background(Color.dialogOrange)
        .clipShape(RoundedRectangle(cornerRadius: 10))
        .shadow(radius: 10)
        .padding(30)
        .offset(x: 0, y: offset)
        .onAppear {
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
struct CustomCollectionDialog_Previews: PreviewProvider {
    static var previews: some View {
        CustomCollectionDialog(isActive: .constant(true), title: "Save to Collection", action: {}, buttoTitle: "Done")
    }
}

public struct PlaceholderStyleCollections: ViewModifier {
    var showPlaceHolder: Bool
    var placeholder: String

    public func body(content: Content) -> some View {
        ZStack(alignment: .leading) {
            if showPlaceHolder {
                Text(placeholder)
                    .foregroundColor(.textBlack)
                    .opacity(0.7)
            }
            content
        }
    }
}

