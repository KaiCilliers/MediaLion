import SwiftUI
import shared

struct ContentView: View {
    
    @State var selectedTab: MLTabDestinations = .discovery
    
	var body: some View {
        NavigationStack {
            VStack(spacing: 0) {
                
                TabView(selection: $selectedTab) {
                    DiscoveryScreen()
                        .tag(MLTabDestinations.discovery)
                    
                    CollectionsScreen()
                        .tag(MLTabDestinations.collection)
                }
                .navigationDestination(for: MLRootDestinations.self) { destination in
                    switch (destination) {
                        
                        case .search:
                            SearchScreen()
                            
                    }
                }
                
                MLBottomBar(selectedTab: $selectedTab)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

