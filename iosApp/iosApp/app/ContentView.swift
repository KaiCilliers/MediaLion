import SwiftUI
import shared

struct ContentView: View {
    
    @State var selectedTab: String = "Discovery"
    
	var body: some View {
        VStack(spacing: 0) {
            
            TabView(selection: $selectedTab) {
                CollectionsScreen()
                    .tag("My Collection")
                
                DiscoveryScreen()
                    .tag("Discovery")
            }
            
            MLBottomBar { tab in
                selectedTab = tab.title
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

