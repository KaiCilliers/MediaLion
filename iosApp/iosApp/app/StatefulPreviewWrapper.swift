//
//  StatefulPreviewWrapper.swift
//  iosApp
//
//  Created by Nadine Cilliers on 06/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI


public struct StatefulPreviewWrapper<Value, Content: View>: View {
    @State var value: Value
    var content: (Binding<Value>) -> Content

    public var body: some View {
        content($value)
    }

    public init(_ value: Value, content: @escaping (Binding<Value>) -> Content) {
        self._value = State(wrappedValue: value)
        self.content = content
    }
}
