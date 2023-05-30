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
    let genres: [GenreWithType]
    let title: String
    let onClose: (GenreWithType) -> Void
    
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
                        ForEach(genres) { genre in
                            Text("\(genre.genre.name) - \(genre.mediaType)")
                                .onTapGesture {
                                    onClose(genre)
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
        
        CustomCategoriesDialog(
            genres: categories.map({ item in
                print("deadpool - \(item.name)")
                return GenreWithType(
                    genre: Genre(
                        id: Int32(Int(Int.random(in: 0..<100000))),
                        name: "\(item.name)",
                        mediaType: MediaType.movie
                    ), mediaType: MediaType.movie
                )
            }), title: "Categories",
            onClose: {_ in}
        )
    }
}
