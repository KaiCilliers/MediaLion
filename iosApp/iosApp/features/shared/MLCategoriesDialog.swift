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

struct MLCategoriesDialog: View {
    
    @State private var offset: CGFloat = 1000
    let state: CategoriesUIState
    let onDismiss: () -> Void
    let onSelection: (MediaCategoryIOS) -> Void
    
    var body: some View  {
        switch (state) {
        case let content as CategoriesUIStateContent:
            VStack (alignment: .center, spacing: 0){
                Text(StringRes.categories)
                    .padding()
                    .customFont(.subtitle1)
                    .foregroundColor(.textBlack)
                
                ScrollView {
                    HStack{
                        Spacer()
                        VStack(alignment: .center, spacing: 8) {
                            ForEach(content.categories.map({ domain in
                                MediaCategoryIOS(origin: domain)
                            })) { category in
                                Text("\(category.origin.name())")
                                    .onTapGesture {
                                        onSelection(category)
                                        onDismiss()
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
        default: ProgressView()
        }
    }
    
    
    func close() {
        withAnimation(.spring()) {
            offset = 1000
        }
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
            onDismiss()
        }
    }
}

struct customCategoriesDialog_Previews: PreviewProvider {
    static var previews: some View {
        let categories = [
            MediaCategoryD(id: "Horror", name: "Horror", appliesToType: MediaTypeNew.All()),
            MediaCategoryD(id: "Romance", name: "Romance", appliesToType: MediaTypeNew.All()),
            MediaCategoryD(id: "Comedy", name: "Comedy", appliesToType: MediaTypeNew.All()),
            MediaCategoryD(id: "Thriller", name: "Thriller", appliesToType: MediaTypeNew.All()),
            MediaCategoryD(id: "Crime", name: "Crime", appliesToType: MediaTypeNew.All()),
            MediaCategoryD(id: "Drama", name: "Drama", appliesToType: MediaTypeNew.All()),
            MediaCategoryD(id: "Fantasy", name: "Fantasy", appliesToType: MediaTypeNew.All()),
            MediaCategoryD(id: "Rom-Com", name: "Rom-Com", appliesToType: MediaTypeNew.All()),
            MediaCategoryD(id: "Action", name: "Action", appliesToType: MediaTypeNew.All()),
            MediaCategoryD(id: "Animation", name: "Animation", appliesToType: MediaTypeNew.All()),
            MediaCategoryD(id: "Sci-Fi", name: "Sci-Fi", appliesToType: MediaTypeNew.All()),
        ]
        
        let state = CategoriesUIStateContent(
            categories: categories
        )
        
        MLCategoriesDialog(
            state: state,
            onDismiss: {},
            onSelection: { selection in }
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
