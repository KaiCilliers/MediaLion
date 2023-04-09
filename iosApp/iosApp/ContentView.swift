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
    let asd = SharedRes.colors().themedColor.getUIColor()
    
	var body: some View {
        VStack {
            Slider(value: $sliderValue, in: 0...5, step: 1)
            Text(SharedTextResource().getMyPluralFormattedDesc(quantity: Int32(Int(sliderValue))).localized())
            if #available(iOS 15.0, *) {
                Text(nullUser + " / " + legitUser + " / " + sharedText)
                    .foregroundColor(Color(uiColor: asd))
                    .font(Font(s))
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
