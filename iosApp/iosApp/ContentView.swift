import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greet()

	var body: some View {
        VStack(spacing: 30) {
            NavigationLink(destination: SecondScreen(choice: "NOICE")) {
                Text(greet)
            }
            NavigationLink(destination: SecondScreen(choice: "OTHER")) {
                Text("greet")
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct SecondScreen: View {
    var choice: String
    @State private var navigateToThirdScreen = false
    @State private var hideToolbar = false
    @EnvironmentObject var user: User
    
    var body: some View {
        VStack {
            Text("Score = \(user.score)")
            NavigationLink(destination: ThirdScreen(), isActive: $navigateToThirdScreen) { EmptyView() }
            Button("Tap to go to 3rd Screen") {
                self.navigateToThirdScreen = true
                self.hideToolbar = true
            }
            Button("Increase user score by 1") {
                self.user.score += 1
                self.hideToolbar = false
            }
            Text("Hello, I am Android - You chose \(choice)")
                .navigationTitle("Second Screen")
                .navigationBarHidden($hideToolbar.wrappedValue)
        }
    }
}

struct ThirdScreen: View {
    @State private var navigationSelection: String? = nil
    @State private var hideBackButton = true
    
    var body: some View {
        VStack {
            Button("Toggle Back button visibility") {
                self.hideBackButton = !hideBackButton
            }
            NavigationLink(destination: FourthScreen(), tag: "4", selection: $navigationSelection) { EmptyView() }
            NavigationLink(destination: FithScreen(), tag: "5", selection: $navigationSelection) { EmptyView() }
            Button("Tap to show 4th Screen") {
                self.navigationSelection = "4"
            }
            Button("Tap to show 5th Screen") {
                self.navigationSelection = "5"
            }
        }
        .navigationTitle("Screen 3")
        .navigationBarBackButtonHidden($hideBackButton.wrappedValue)
    }
}

struct FourthScreen: View {
    @State private var points = 0
    @State private var goToNumbaSix = false
    
    var body: some View {
        VStack(spacing: 40) {
            NavigationLink(destination: ScreenSix(), isActive: $goToNumbaSix) { EmptyView() }
            Text("Total points: \(points)")
            Text("May the 4th be with you!")
                .navigationTitle("Screen 4")
                .navigationBarItems(
                    leading: Button("Numba 6") { self.goToNumbaSix = true },
                    trailing: Button("Add 1") { self.points += 1}
                )
        }
    }
}

struct FithScreen: View {
    @State private var showDetailScreen = false
    
    var body: some View {
        NavigationView {
            NavigationLink(destination: Text("ANother Screen"), isActive: $showDetailScreen) {
                Text("Revenge of the 5th!")
                    .navigationTitle("Screen 5")
                    .navigationBarTitleDisplayMode(.inline)
            }
        }
        // not too important behaviour
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                self.showDetailScreen = false
            }
        }
    }
}

struct ScreenSix: View {
    @EnvironmentObject var user: User
    
    var body: some View {
        VStack {
            Text("Score: \(user.score)")
            Text("Screen 6 :)")
                .navigationTitle("Ok genoeg nou...")
                .navigationBarItems(
                    trailing: HStack {
                        Button("Subtract 1") { self.user.score -= 1 }
                        Button("Add 1") { self.user.score += 1 }
                    }
                )
        }
    }
}
