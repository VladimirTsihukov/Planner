import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        ComposeView()
    }
}

struct ComposeView : UIViewControllerRepresentable {
    typealias UIViewControllerType = UIViewController
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) { }
    
    func makeUIViewController(context: Context) -> UIViewControllerType {
        RootViewKt.MainViewController()
    }
}
