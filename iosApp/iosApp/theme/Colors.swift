//
//  Colors.swift
//  iosApp
//
//  Created by Nadine Cilliers on 09/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import SwiftUI

extension SwiftUI.Color {
    static let background = Color(ColorRes().background.getUIColor())
    static let secondary = Color(ColorRes().secondary.getUIColor())
    static let mlPrimary = Color(ColorRes().primary.getUIColor())
    static let primaryVariantBlue = Color(ColorRes().primaryVariantBlue.getUIColor())
    static let primaryVariant = Color(ColorRes().primaryVariant.getUIColor())
    static let textBlack = Color(ColorRes().textBlack.getUIColor())
    static let newListTextField = Color(ColorRes().newListTextField.getUIColor())
    static let dialogOrange = Color(ColorRes().dialogOrange.getUIColor())
}
