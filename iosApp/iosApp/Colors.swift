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
    static let primary = Color(SharedRes.colors().primary.getUIColor())
    static let primaryVariant = Color(SharedRes.colors().primaryVariant.getUIColor())
    static let secondary = Color(SharedRes.colors().secondary.getUIColor())
}
