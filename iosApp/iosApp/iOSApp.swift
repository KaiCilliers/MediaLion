import SwiftUI
import shared

@main
struct iOSApp: App {
    @ObservedObject private(set) var viewModel = ViewModel()
    
	var body: some Scene {
		WindowGroup {
            NavigationView {
                ContentView()
            }
            .navigationTitle("ContentView")
            .environmentObject(viewModel)
		}
	}
}


class ViewModel: ObservableObject {
    @Published var text = "Loading..."
    
    init() {
        DiscoveryComponent().allLaunches { text, error in
            DispatchQueue.main.async {
                sleep(2)
                if let text = text {
                    self.text = text
                } else {
                    self.text = error?.localizedDescription ?? "error"
                }
            }
        }
    }
}
