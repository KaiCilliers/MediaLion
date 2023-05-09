//
//  DetailPreviewTest.swift
//  iosApp
//
//  Created by Nadine Cilliers on 30/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct DetailPreviewTest: View {
    let onCloseClick: () -> ()
    var body: some View {
        ZStack{

            LinearGradient(gradient: Gradient(colors: [.mlPrimary, .primaryVariantBlue]), startPoint: .top, endPoint: .bottom).edgesIgnoringSafeArea(.all)
            
            VStack{
                HStack{
                    MLMediaPoster()
                        .frame(width: 80, height: 120)
                        .padding(.leading)
                        .padding(.trailing, 8)
                        .padding(.bottom)
                    
                        
                    
                    
                    VStack{
                        Text("Harry Potter")
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity, alignment: .topLeading)
                            .customFont(.h3)
                            
                        
                    
                        
                        Text("2001")
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity, alignment: .topLeading)
                            .padding(.bottom, 2)
                            .customFont(.h1)
                        
                       
                        
                        Text("Throughout the series, Harry is described as having his father's perpetually untidy black hair, his mother's bright green eyes, and a lightning bolt-shaped scar on his forehead. He is further described as small and skinny for his age with a thin face and knobbly knees, and he wears Windsor glasses.")
                        
                            
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity, alignment: .topLeading)
                            .customFont(.body2)
                            .lineSpacing(2)
                        
                        
                        
                     
                    }
                    Button {
                        onCloseClick()
                    }label: {
                        Image("closeIcon")
                            .resizable()
                            .frame(width: 30, height: 30)
                            .padding(.bottom, 105)
                            .padding(.trailing)
                            .padding(.leading, 5)
                    }
                    
                }
                Spacer()
                    .frame(height: 30)
                
                Image("addToListIcon")
                    .resizable()
                    .frame(width: 40, height: 40)
                
                Text(StringRes.mtList)
                    .customFont(.h1)
                    .foregroundColor(.white)
            }
            
           
            
           
            
           
            

            
        }
    
        

    }
}

struct DetailPreviewTest_Previews: PreviewProvider {
    static var previews: some View {
        DetailPreviewTest(onCloseClick: {})
    }
}
