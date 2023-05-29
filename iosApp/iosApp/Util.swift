//
//  Util.swift
//  iosApp
//
//  Created by Nadine Cilliers on 29/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import SwiftUI

extension Binding {
     func toUnwrapped<T>(defaultValue: T) -> Binding<T> where Value == Optional<T>  {
        Binding<T>(get: { self.wrappedValue ?? defaultValue }, set: { self.wrappedValue = $0 })
    }
}
