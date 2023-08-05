import SwiftUI
import shared
import Firebase

class AppDelegate: NSObject, UIApplicationDelegate {
  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
      var filePath:String!
            #if DEBUG
                print("[FIREBASE] Development mode.")
                filePath = Bundle.main.path(forResource: "GoogleService-Info", ofType: "plist", inDirectory: "debug")
            #else
                print("[FIREBASE] Production mode.")
                filePath = Bundle.main.path(forResource: "GoogleService-Info", ofType: "plist", inDirectory: "release")
            #endif

            let options = FirebaseOptions.init(contentsOfFile: filePath)!
            FirebaseApp.configure(options: options)
          return true
  }
}


@main
struct iOSApp: App {
    
    // register app delegate for Firebase setup
    @UIApplicationDelegateAdaptor (AppDelegate.self) var delegate
    
    init() {
        KoinIOSWrapperKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
            NavigationStack {
                Text("noice")
            }
		}
	}
}
