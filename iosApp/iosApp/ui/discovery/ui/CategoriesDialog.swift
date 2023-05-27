//
//  categoriesDialog.swift
//  iosApp
//
//  Created by Nadine Cilliers on 02/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CategoriesDialog: View {
    let genres: [GenreWithType]
    let onCategorySelection: (GenreWithType) -> Void
    
    @State var isActive : Bool = false
    var body: some View {
        ZStack{
            VStack{
                Button{
                    isActive = true
                } label: {
                    Text(StringRes.categories)
                        .foregroundColor(.mlLightBlue)
                        .background(Color.background)
                        .customFont(.h3)
                }
            }
            .padding()
            
            if isActive {
                CustomCategoriesDialog(
                    genres: genres,
                    title: StringRes.categories,
                    onClose: {}
                )
            }
        }
    }
}

struct categoriesDialog_Previews: PreviewProvider {
    static var previews: some View {
        
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
        
        CategoriesDialog(
            genres: categories.map({ item in
                GenreWithType(genre: Genre(id: Int32(Int(Int.random(in: 0..<100000))), name: "\(item.name)"), mediaType: MediaType.movie)
            }),
            onCategorySelection: {_ in}
        )
        Text("")
    }
}

struct GenreWithType: Identifiable {
    let id: Int
    let genre: Genre
    let mediaType: MediaType
    
    init(genre: Genre, mediaType: MediaType) {
        self.genre = genre
        self.mediaType = mediaType
        self.id = Int(genre.id)
    }
}
