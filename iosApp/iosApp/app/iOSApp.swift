import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        KoinIOSWrapperKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
            NavigationView {
                ContentView()
            }
            .navigationTitle("SearchScreen")
		}
	}
}
