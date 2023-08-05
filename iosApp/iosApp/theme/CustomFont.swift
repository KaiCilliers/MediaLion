//
//  CustomFont.swift
//  iosApp
//
//  Created by Nadine Cilliers on 09/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CustomFont : ViewModifier {
    @Environment(\.sizeCategory) var sizeCategory
    
    public enum TextStyle {
        case h1
        case h2
        case h3
        case h4
        case h5
        case body1
        case body2
        case subtitle1
        case subtitle2
    }
    
    var textStyle: TextStyle
    
    func body(content: _ViewModifier_Content<Self>) -> some View {
           let scaledSize = UIFontMetrics.default.scaledValue(for: size)
        return content.font(Font(fontName.uiFont(withSize: scaledSize)))
        }
        
        private var fontName: FontResource {
            switch textStyle {
            case .h1:
                return FontRes().primaryRegular
            case .h2:
                return FontRes().primaryBold
            case .h3:
                return FontRes().primaryBold
            case .h4:
                return FontRes().primaryBold
            case .h5:
                return FontRes().primaryBold
            case .subtitle1:
                return FontRes().primaryBold
            case .subtitle2:
                return FontRes().primaryRegular
            case .body1:
                return FontRes().primaryRegular
            case .body2:
                return FontRes().primaryRegular
            }
        }
        
        private var size: CGFloat {
            switch textStyle {
            case .h1:
                return 12
            case .h2:
                return 30
            case .h3:
                return 16
            case .h4:
                return 14
            case .h5:
                return 42
            case .subtitle1:
                return 20
            case .subtitle2:
                return 16
            case .body1:
                return 8
            case .body2:
                return 10
            }
        }
}

extension View {
    
    func customFont(_ textStyle: CustomFont.TextStyle) -> some View {
        self.modifier(CustomFont(textStyle: textStyle))
    }
}
