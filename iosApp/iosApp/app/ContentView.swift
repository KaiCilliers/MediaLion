import SwiftUI
import shared

struct ContentView: View {
    
    @State var selectedTab: MLTabDestinations = .discovery
    @State var showAboutDialog: Bool = false
    
    var body: some View {
        
        var screenBlurAmount: Float = {
            if showAboutDialog {
                return 4
            } else {
                return 0
            }
        }()
        ZStack {
            VStack(spacing: 0) {
                
                TabView(selection: $selectedTab) {
                    DiscoveryScreen(
                        onInfoClicked: { showAboutDialog = true }
                    )
                    .tag(MLTabDestinations.discovery)
                    
                    CollectionsScreen(
                        onInfoClicked: {
                            showAboutDialog = true
                        }
                    )
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
            .blur(radius: CGFloat(screenBlurAmount))
            .disabled(showAboutDialog)
            
            if showAboutDialog {
                MLAboutDialog(
                    onCloseAction: {
                        showAboutDialog = false
                    }
                )
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

