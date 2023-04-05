import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()

	var body: some View {
        CardSuitsScreen()
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct CardSuitsScreen: View {
    var listContent = ["diamonds", "clubs", "hearts", "spades"]
    
    var body: some View {
        VStack {
            List(listContent,id: \.self) { rowValue in
                NavigationLink(destination: LimitedDetailScreen(item: rowValue)) {
                    ItemRow(item: rowValue)
                }
            }
            .listStyle(.automatic)
        }
        .navigationTitle("Card suits")
        .navigationBarTitleDisplayMode(.large)
    }
}
