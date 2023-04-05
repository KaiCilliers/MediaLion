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
    @StateObject var user = User(color: Color.black)
    
    var body: some View {
        ContentView()
    }
}

class User: ObservableObject {
    var color: Color
    @Published var score = 0
    
    init(color: Color, score: Int = 0) {
        self.color = color
        self.score = score
    }
}

struct iOSApp_Preview: PreviewProvider {
    static var previews: some View {
        CoreScreenContent()
    }
}
