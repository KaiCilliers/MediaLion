//
//  DashedOutline.swift
//  iosApp
//
//  Created by Nadine Cilliers on 10/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLEmptyCollectionView: View {
    
    let radius: CGFloat = 100
    let pi = Double.pi
    let dotCount = 25
    let dotLength: CGFloat = 5
    let spaceLength: CGFloat

    init() {
        let circumerence: CGFloat = CGFloat(2.0 * pi) * radius
        spaceLength = circumerence / CGFloat(dotCount) - dotLength
    }
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerSize: CGSize(width: 10.0, height: 10.0))
                .stroke(Color.dialogOrange, style: StrokeStyle(lineWidth: 6, lineCap: .square, lineJoin: .round, miterLimit: 0, dash: [dotLength, spaceLength], dashPhase: 0))
                .frame(width: .infinity, height: 170)
            
            Text("Tap here to start adding media")
                .customFont(.subtitle1)
                .padding()
                .foregroundColor(.white)
        }
        
            .padding(10)
            .background(Color.background)
    }
}

struct DashedOutline_Previews: PreviewProvider {
    static var previews: some View {
        MLEmptyCollectionView()
    }
}
