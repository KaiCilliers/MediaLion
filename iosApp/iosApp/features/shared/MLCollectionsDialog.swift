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
    let targetMediaItem: SingleMediaItemD
    let miniCollectionUiState: MiniCollectionUIState
    let onUpdateCollection: (CollectionNew) -> Void
    let onCreateCollection: (CollectionNew) -> Void
    
    var body: some View {
        VStack{
            
            switch (miniCollectionUiState) {
                case let content as Content:
                    Text(StringRes.saveToListTitle)
                        .customFont(.subtitle1)
                        .padding()
                        .foregroundColor(.textBlack)
                    
                    
                    ScrollView {
                        VStack(spacing: 15) {
                            let iosCollections = content.collections.map { item in
                                CollectionsNewIOS(origin: item)
                            }
                            ForEach(iosCollections) { collectionItem in
                                MLCollectionListItem(
                                    collectionName: "\(collectionItem.origin.title())",
                                    checked: collectionItem.origin.media().map({ item in
                                        item.identifier().uniqueIdentifier()
                                    }).contains { id in
                                        return id == targetMediaItem.identifier().uniqueIdentifier()
                                    },
                                    onCollectionClicked: { favorited in
                                        print("IOS - collection clicked \(collectionItem.origin.title()) -> \(favorited)")
                                        if (favorited) {
                                            collectionItem.origin.add(item: targetMediaItem)
                                        } else {
                                            collectionItem.origin.remove(item: targetMediaItem)
                                        }
                                        onUpdateCollection(collectionItem.origin)
                                    }
                               )
                                    .background(Color.dialogOrange)
                                    .foregroundColor(.textBlack)
                            }
                        }
                    }.frame(height: 150)
                    
                    ZStack{
                        HStack{
                            TextField("", text: $newCollectionName)
                                .customFont(.h1)
                                .foregroundColor(.textBlack)
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
                                        let newCollection = CollectionNewDef(
                                            name: self.newCollectionName.trimmingCharacters(in: .whitespacesAndNewlines), mediaItem: targetMediaItem)
                                        onCreateCollection(newCollection)
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
                        .padding(.horizontal, 50)
                        .padding(.vertical)
                    }
            case let _ as Loading: ProgressView()
            default:
                Text("Failure")
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
        let targetedItem = SingleMediaItemMovie(name: "Movie One")
        let state: MiniCollectionUIState = Content(
            stateId: IDDef(),
            collections: [
                CollectionNewDef(name: "Must Watch", mediaItem: targetedItem),
                CollectionNewDef(name: "Favorites"),
                CollectionNewDef(name: "Scary"),
                CollectionNewDef(name: "Weekend"),
            ]
        )
        MLCollectionsDialog(
            onDismiss: {},
            targetMediaItem: targetedItem,
            miniCollectionUiState: state,
            onUpdateCollection: {_ in},
            onCreateCollection: {_ in}
        )
    }
}

private struct PlaceholderStyleCollections: ViewModifier {
    var showPlaceHolder: Bool
    var placeholder: String

    public func body(content: _ViewModifier_Content<Self>) -> some View {
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
                let state: MiniCollectionUIState = Content(
                    stateId: IDDef(),
                    collections: [
                        CollectionNewDef(name: "Must Watch"),
                        CollectionNewDef(name: "Favorites"),
                        CollectionNewDef(name: "Scary"),
                        CollectionNewDef(name: "Weekend"),
                    ]
                )
                MLCollectionsDialog(
                    onDismiss: {},
                    targetMediaItem: SingleMediaItemMovie(name: "Movie One"),
                    miniCollectionUiState: state,
                    onUpdateCollection: {_ in},
                    onCreateCollection: {_ in}
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

struct CollectionsNewIOS : Identifiable {
    let origin: CollectionNew
    var id: ObjectIdentifier
    
    init(origin: CollectionNew) {
        self.origin = origin
        self.id = ObjectIdentifier(origin.identifier())
    }
}
