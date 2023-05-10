//
//  CustomCollectionDialog.swift
//  iosApp
//
//  Created by Nadine Cilliers on 30/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MLCollectionsDialog: View {
    
    @State private var offset: CGFloat = 1000
    @State private var newCollectionName: String = ""
    
    let onDismiss: () -> Void
    let collections: [CollectionItem]
    let onAddToCollection: (String) -> Void
    let onRemoveFromCollection: (String) -> Void
    let onCreateNewCollection: (String) -> Void
    
    var body: some View {
        VStack{
            Text(StringRes.saveToListTitle)
                .customFont(.subtitle1)
                .padding()
            
            
            ScrollView {
                VStack(spacing: 15) {
                    ForEach(collections, id: \.self) { collectionItem in
                        MLCollectionListItem(
                            collectionName: collectionItem.name,
                            checked: collectionItem.checked,
                            onCollectionClicked: { favorited in
                                print("IOS - collection clicked \(collectionItem.name) -> \(favorited)")
                                if (favorited) {
                                    onAddToCollection(collectionItem.name)
                                } else { onRemoveFromCollection(collectionItem.name)
                                }
                            }
                       )
                            .background(Color.dialogOrange)
                    }
                }
            }.frame(height: 150)
            
            ZStack{
                HStack{
                    TextField("", text: $newCollectionName)
                        .customFont(.h1)
                        .modifier(
                            PlaceholderStyleCollections(
                                showPlaceHolder: newCollectionName.isEmpty,
                                placeholder: StringRes.emptyAddToListText
                            )
                        )
                        .padding(.vertical)
                        .padding(.horizontal)
                    
                    if !newCollectionName.isEmpty {
                        Button(
                            action: {
                                onCreateNewCollection(self.newCollectionName)
                                self.newCollectionName = ""
                            },
                            label: {
                                Image("nameCreated")
                                    .resizable()
                                    .frame(width: 20, height: 20)
                                    .padding()
                            }
                        )
                    }
                }
                    
            }.background(Color.newListTextField)
                .padding(.horizontal)
                .padding(.top)
            
            
            Button{
                close()
            } label: {
                ZStack{
                    RoundedRectangle(cornerRadius: 10)
                        .foregroundColor(.mlPrimary)
                    Text("Done")
                        .foregroundColor(.white)
                        .padding()
                }
                .padding(.horizontal, 100)
                .padding(.vertical)
            }
            
        }
        .fixedSize(horizontal: false, vertical: true)
        .padding()
        .background(Color.dialogOrange)
        .clipShape(RoundedRectangle(cornerRadius: 10))
        .shadow(radius: 10)
        .padding(30)
        .offset(x: 0, y: offset)
        .onAppear {
            withAnimation(.spring()) {
                offset = 0
            }
        }
    }
    
    func close() {
        withAnimation(.spring()) {
            offset = 1000
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
                onDismiss()
            }
        }
    }
}

struct MLCollectionsDialog_Previews: PreviewProvider {
    static var previews: some View {
        MLCollectionsDialog(
            onDismiss: {},
            collections: [
                CollectionItem(name: "Must Watch", checked: false),
                CollectionItem(name: "Favorites", checked: true),
                CollectionItem(name: "Scary", checked: false),
                CollectionItem(name: "Weekend", checked: true),
            ],
            onAddToCollection: {_ in},
            onRemoveFromCollection: {_ in},
            onCreateNewCollection: {_ in}
        )
    }
}

private struct PlaceholderStyleCollections: ViewModifier {
    var showPlaceHolder: Bool
    var placeholder: String

    public func body(content: Content) -> some View {
        ZStack(alignment: .leading) {
            if showPlaceHolder {
                Text(placeholder)
                    .foregroundColor(.textBlack)
                    .opacity(0.7)
            }
            content
        }
    }
}

private struct SaveToCollectionScreen: View {
    
    @State var isActive: Bool = false
    
    var body: some View {
        ZStack{
            VStack{
                Button{
                    isActive = true
                    
                } label: {
                    Image("addToListIcon")
                }
            }
            .padding()
            
            if isActive {
                MLCollectionsDialog(
                    onDismiss: {},
                    collections: [],
                    onAddToCollection: {_ in},
                    onRemoveFromCollection: {_ in},
                    onCreateNewCollection: {_ in}
                )
            }
        }
    }
}

struct SaveToCollectionScreen_Previews: PreviewProvider {
    static var previews: some View {
        SaveToCollectionScreen()
    }
}

struct CollectionItem : Hashable{
    let name: String
    let checked: Bool
}
