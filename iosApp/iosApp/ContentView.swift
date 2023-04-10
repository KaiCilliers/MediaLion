import SwiftUI
import shared

struct ContentView: View {
    @EnvironmentObject var viewModel: ViewModel
    
	var body: some View {
        NavigationLink(destination: ListView()) {
            VStack {
                Text("heading 1").myFont(.h1).foregroundColor(.primary)
                Text("heading 2").myFont(.h2).foregroundColor(.primaryVariant)
                Text("heading 3").myFont(.h3).foregroundColor(.secondary)
                Text("subtitle 1").myFont(.subtitle1)
                Text("subtitle 2").myFont(.subtitle2)
                Text("body").myFont(.body)
            }
        }
    }
}

struct ListView: View {
    @EnvironmentObject var viewModel: ViewModel
    
    var body: some View {
        if #available(iOS 15.0, *) {
            List(viewModel.mediaList, id: \.self) { item in
                AsyncImage(url: URL(string: DiscoveryComponent.Companion().baseUrl + (item.posterPath ?? "")))
                Spacer()
            }
        } else {
            // Fallback on earlier versions
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
