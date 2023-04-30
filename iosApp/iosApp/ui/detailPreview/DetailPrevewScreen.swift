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
    @State private var showingDetailPreview = true
    var body: some View {
        Button("show bottom sheet"){
            showingDetailPreview.toggle()
        }
        .sheet(isPresented: $showingDetailPreview){
            
            DetailPreviewTest(onCloseClick: {showingDetailPreview = false})
   
           
            .presentationDetents([.medium, .fraction(0.4)])
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
