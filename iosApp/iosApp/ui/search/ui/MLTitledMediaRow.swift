//
//  MLTitledMediaRow.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLTitledMediaRow: View {
    var body: some View {
        VStack{
            Text("Related Movie Title").foregroundColor(.white).customFont(.h2).frame(maxWidth: .infinity, alignment: .leading)
            ScrollView(.horizontal) {
                HStack(spacing: 20) {
                    ForEach(0...20, id: \.self) {value in
                        MLMediaPoster()
                            .frame(width: 100, height: 170)
                            
                    }
                }
            }
            
        }
        .padding()
        .background(Color.background)
        
    }
}

struct MLTitledMediaRow_Previews: PreviewProvider {
    static var previews: some View {
        MLTitledMediaRow()
    }
}
