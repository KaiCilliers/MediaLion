import SwiftUI
import shared

struct ContentView: View {
    let greet = SharedTextResource().getGreetingWithName(name: "Nadine").localized()
    @State var sliderValue: Double = 0

	var body: some View {
        VStack {
            Slider(value: $sliderValue, in: 0...5, step: 1)
            Text(SharedTextResource().getMyPluralFormattedDesc(quantity: Int32(Int(sliderValue))).localized())
            Text(greet)
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
