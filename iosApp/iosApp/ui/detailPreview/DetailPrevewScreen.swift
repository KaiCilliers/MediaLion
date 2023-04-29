//
//  DetailPrevewScreen.swift
//  iosApp
//
//  Created by Nadine Cilliers on 15/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 16.0, *)
struct DetailPrevewScreen: View {
    @State private var showingDetailPreview = false
    var body: some View {
        Button("show bottom sheet"){
            showingDetailPreview.toggle()
        }
        .sheet(isPresented: $showingDetailPreview){
            ZStack{
                Color.background.edgesIgnoringSafeArea(.all)
                VStack{
                    HStack{
                        MLMediaPoster()
                            .frame(width: 100, height: 175)
                            .padding()
                        VStack{
                            Text("Harry Potter")
                                .foregroundColor(.white)
                                .frame(maxWidth: .infinity, alignment: .topLeading)
                            Text("2001")
                                .foregroundColor(.white)
                                .frame(maxWidth: .infinity, alignment: .topLeading)
                            Text("Throughout the series, Harry is described as having his father's perpetually untidy black hair, his mother's bright green eyes, and a lightning bolt-shaped scar on his forehead. He is further described as small and skinny for his age with a thin face and knobbly knees, and he wears Windsor glasses.")
                                .foregroundColor(.white)
                                .frame(maxWidth: .infinity, alignment: .topLeading)
                        }.frame(maxWidth: .infinity, alignment: .topLeading)
                        Image("closeIcon")
                            .resizable()
                            .frame(width: 30, height: 30)
                            .frame(maxWidth: .infinity, alignment: .topTrailing)
                        
                    }
                    Image("addToListIcon")
                        .resizable()
                        .frame(width: 40, height: 40)
                        .padding(.top, 20)
                    Text("My List")
                        .foregroundColor(.white)
                        .padding(.bottom)
                        .customFont(.subtitle2)
                }
            }
        
   
           
            .presentationDetents([.medium, .fraction(0.8)])
            .presentationDragIndicator(.hidden)
         
            
           
        }
        
    }
    
}

@available(iOS 16.0, *)
struct DetailPrevewScreen_Previews: PreviewProvider {
    static var previews: some View {
        DetailPrevewScreen()
    }
}
