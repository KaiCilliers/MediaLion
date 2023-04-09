import SwiftUI
import shared

struct ContentView: View {
    let greet = SharedTextResource().getGreetingWithName(name: "Nadine").localized()
    @State var sliderValue: Double = 0
    let sharedText = SharedRes.strings().greeting.desc().localized()
    let nullUser = SharedTextResource().getUserName(user: nil).localized()
    let legitUser = SharedTextResource().getUserName(user: "Nadine").localized()
    let s = SharedRes.fontsQuicksand().boldItalic.uiFont(withSize: 40)
    // toggle dark mode in emulator CMD + Shift + A
    let asd = SharedRes.colors().primary.getUIColor()
    
	var body: some View {
        VStack {
            Text("heading 1").myFont(.h1)
            Text("heading 2").myFont(.h2)
            Text("heading 3").myFont(.h3)
            Text("subtitle 1").myFont(.subtitle1)
            Text("subtitle 2").myFont(.subtitle2)
            Text("body").myFont(.body)
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct MyFont : ViewModifier {
    @Environment(\.sizeCategory) var sizeCategory
    
    public enum TextStyle {
        case h1
        case h2
        case h3
        case body
        case subtitle1
        case subtitle2
    }
    
    var textStyle: TextStyle
    
    func body(content: Content) -> some View {
           let scaledSize = UIFontMetrics.default.scaledValue(for: size)
        return content.font(Font(fontName.uiFont(withSize: scaledSize)))
        }
        
        private var fontName: FontResource {
            switch textStyle {
            case .h1:
                return SharedRes.fontsQuicksand().bold
            case .h2:
                return SharedRes.fontsQuicksand().boldItalic
            case .h3:
                return SharedRes.fontsQuicksand().bold
            case .subtitle1:
                return SharedRes.fontsQuicksand().light
            case .subtitle2:
                return SharedRes.fontsQuicksand().lightItalic
            case .body:
                return SharedRes.fontsQuicksand().regular
            }
        }
        
        private var size: CGFloat {
            switch textStyle {
            case .h1:
                return 42
            case .h2:
                return 30
            case .h3:
                return 24
            case .subtitle1:
                return 20
            case .subtitle2:
                return 16
            case .body:
                return 14
            }
        }
}

extension View {
    
    func myFont(_ textStyle: MyFont.TextStyle) -> some View {
        self.modifier(MyFont(textStyle: textStyle))
    }
}
