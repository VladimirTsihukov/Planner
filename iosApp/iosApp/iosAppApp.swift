import shared
import SwiftUI

@main
struct iosAppApp: App {

    init() {
        IosKoin.shared.initialize(userDefault: UserDefaults.standard)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
