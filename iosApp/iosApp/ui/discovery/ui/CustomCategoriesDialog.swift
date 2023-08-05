//
//  customCategoriesDialog.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct Category : Identifiable {
    var id: Int
    
    let name: String
}

struct CustomCategoriesDialog: View {
    
    @State private var offset: CGFloat = 1000
    let categories: [MediaCategoryIOS]
    let title: String
    let onClose: (MediaCategoryIOS) -> Void
    
    var body: some View  {
        
        VStack (alignment: .center, spacing: 0){
            Text(title)
                .padding()
                .customFont(.subtitle1)
                .foregroundColor(.textBlack)
            
            ScrollView {
                HStack{
                    Spacer()
                    VStack(alignment: .center, spacing: 8) {
                        ForEach(categories) { category in
                            Text("\(category.origin.name())")
                                .onTapGesture {
                                    onClose(category)
                                    close()
                                }
                            
                        }
                    }
                    Spacer()
                }
                
            }
            .frame(height: 270)
            .frame(maxWidth: .infinity, alignment: .leading)
            .customFont(.h4)
            .foregroundColor(.textBlack)
            
            
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
        .onDisappear {
            withAnimation(.spring()) {
                offset = 1000
            }
        }
    }
    
    
    func close() {
        withAnimation(.spring()) {
            offset = 1000
        }
//        DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
//            onClose(nil)
//        }
    }
}

struct customCategoriesDialog_Previews: PreviewProvider {
    static var previews: some View {
        
        let categories = [
            MediaCategoryIOS(origin: MediaCategoryD(id: "Horror")),
            MediaCategoryIOS(origin: MediaCategoryD(id: "Romance")),
            MediaCategoryIOS(origin: MediaCategoryD(id: "Comedy")),
            MediaCategoryIOS(origin: MediaCategoryD(id: "Thriller")),
            MediaCategoryIOS(origin: MediaCategoryD(id: "Crime")),
            MediaCategoryIOS(origin: MediaCategoryD(id: "Drama")),
            MediaCategoryIOS(origin: MediaCategoryD(id: "Fantasy")),
            MediaCategoryIOS(origin: MediaCategoryD(id: "Rom-Com")),
            MediaCategoryIOS(origin: MediaCategoryD(id: "Action")),
            MediaCategoryIOS(origin: MediaCategoryD(id: "Animation")),
            MediaCategoryIOS(origin: MediaCategoryD(id: "Sci-Fi")),
        ]
        
        CustomCategoriesDialog(
            categories: categories,
            title: "Categories",
            onClose: {_ in}
        )
    }
}

struct MediaCategoryIOS : Identifiable {
    let origin: MediaCategory
    var id: ObjectIdentifier
    
    init(origin: MediaCategory) {
        self.origin = origin
        self.id = ObjectIdentifier(origin.identifier())
    }
}
