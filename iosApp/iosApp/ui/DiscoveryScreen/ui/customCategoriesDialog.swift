//
//  customCategoriesDialog.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct Category : Identifiable {
    var id: Int
    
    let name: String
}
@available(iOS 15.0, *)
@available(iOS 15.0, *)
struct customCategoriesDialog: View {
    
    @Binding var isActive: Bool
    @State private var offset: CGFloat = 1000
    let categories = [
        Category(id: 1, name: "Horror"),
        Category(id: 2, name: "Romance"),
        Category(id: 3, name:"Comedy"),
        Category(id: 4, name:"Thriller"),
        Category(id: 5, name:"Crime"),
        Category(id: 6, name:"Drama"),
        Category(id: 7, name:"Fantasy"),
        Category(id: 8, name:"Rom-Com"),
        Category(id: 9, name:"Action"),
        Category(id: 10, name:"Animation"),
        Category(id: 11, name:"Adventure"),
        Category(id: 12, name:"Sci-Fi")
    ]
    let title: String
    let action: () -> ()
    
    
    var body: some View  {
        
        VStack (alignment: .center, spacing: 0){
            Text(title)
                .padding()
                .customFont(.subtitle1)
            
            ScrollView {
                HStack{
                    Spacer()
                    VStack(alignment: .center, spacing: 8) {
                        ForEach(categories) {Category in
                            Text(Category.name)
                            
                        }
                    }
                    Spacer()
                }
                
            }
            .frame(height: 270)
            .frame(maxWidth: .infinity, alignment: .leading)
            .customFont(.h4)
            
            
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
struct customCategoriesDialog_Previews: PreviewProvider {
    static var previews: some View {
        customCategoriesDialog(isActive: .constant(true), title: "Categories", action: {} )
    }
}
