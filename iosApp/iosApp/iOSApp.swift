import SwiftUI

@main
struct iOSApp: App {
	var body: some Scene {
        WindowGroup {
            CoreScreenContent()
        }
	}
}

struct CoreScreenContent: View {
    @StateObject var user = User()
    
    var body: some View {
        
        NavigationView {
            VStack {
                Text("Score: \(user.score)")
                ContentView()
                    .navigationTitle("Screen 1")
                    .navigationBarTitleDisplayMode(.inline)
            }
        }
        .environmentObject(user)
        .statusBar(hidden: true)
    }
}

class User: ObservableObject {
    @Published var score = 0
}

struct iOSApp_Preview: PreviewProvider {
    static var previews: some View {
        CoreScreenContent()
    }
}
