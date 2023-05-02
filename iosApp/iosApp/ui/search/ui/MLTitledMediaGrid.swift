//
//  MLTitledMediaGrid.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLTitledMediaGrid: View {
    let columns = [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())]
    let title : String
    
    var body: some View {
        
       
            ScrollView{
                Text(title).foregroundColor(.white).customFont(.h2).padding(.leading, 16).padding(.top, 8).frame(maxWidth: .infinity, alignment: .leading)
                LazyVGrid(columns: columns, spacing: 16) {
                   
                    ForEach(0...20, id: \.self) {value in
                        MLMediaPoster()
                            .frame(width: 100,height: 170)
                        
                    }
                }
            }
            .background(Color.background)
            .edgesIgnoringSafeArea(.all)
        
        
            
            
        
        
            
        
       
    
        
    }
}

struct MLTitledMediaGrid_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            MLTitledMediaGrid(title: "Top Results")
            
           
        }
    }
}
