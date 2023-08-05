//
//  MLEditCollectionButton.swift
//  iosApp
//
//  Created by Nadine Cilliers on 05/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct MLEditCollectionButton: View {
    
    let isEditing: Bool
    let updateEditing: (Bool) -> Void
    
    var body: some View {
        VStack{
            Spacer()
            HStack{
                Spacer()
                Button {
                    updateEditing(!isEditing)
                } label: {
                    if isEditing {
                        Image("nameCreated")
                            .resizable()
                            .frame(width: 90, height: 90)
                    } else {
                        Image("editIcon")
                            .resizable()
                            .frame(width: 90, height: 90)
                    }
                }
            }
        }
        .padding(.bottom, 20)
        .padding(.trailing, 40)
    }
}

struct MLEditCollectionButtonEditing_Previews: PreviewProvider {
    static var previews: some View {
        MLEditCollectionButton(
            isEditing: true,
            updateEditing: {_ in}
        )
    }
}

struct MLEditCollectionButtonNotEditing_Previews: PreviewProvider {
    static var previews: some View {
        MLEditCollectionButton(
            isEditing: false,
            updateEditing: {_ in}
        )
    }
}
