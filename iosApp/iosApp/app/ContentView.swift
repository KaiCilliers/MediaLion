import SwiftUI
import shared

struct ContentView: View {
    
    @State var selectedTab: MLTabDestinations = .discovery
    @State var showAboutDialog: Bool = false
    @StateObject private var discoveryViewModel = DiscoveryViewModel()
    
    init() {
        NapierProxyKt.debugBuild()
    }
    
    var body: some View {
        
        let screenBlurAmount: Float = {
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
                        onInfoClicked: { showAboutDialog = true },
                        viewModel: discoveryViewModel
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
                            .navigationBarBackButtonHidden(true)
                
                    }
                }.overlay(alignment: .bottom) {
                    MLBottomBar(selectedTab: $selectedTab)
                }
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
        .onAppear {
            print("IOS - discovery - starting to observe viewmodel")
            discoveryViewModel.observe()
            discoveryViewModel.submitAction(action: DiscoveryAction.FetchContent(mediaType: nil))
        }
        .onDisappear {
            print("IOS - discovery - disposing viewmodel")
            discoveryViewModel.dispose()
        }
        .ignoresSafeArea(.keyboard)
    }
    
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

