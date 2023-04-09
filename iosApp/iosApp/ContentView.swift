import SwiftUI
import shared

struct ContentView: View {
    let greet = SharedTextResource().getGreetingWithName(name: "Nadine").localized()
    @State var sliderValue: Double = 0
    let sharedText = SharedRes.strings().greeting.desc().localized()

	var body: some View {
        VStack {
            Slider(value: $sliderValue, in: 0...5, step: 1)
            Text(SharedTextResource().getMyPluralFormattedDesc(quantity: Int32(Int(sliderValue))).localized())
            Text(greet + sharedText)
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
