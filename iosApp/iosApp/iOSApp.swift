import SwiftUI
import shared
import UIKit

struct MainView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {    }
}

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
			MainView()
            .ignoresSafeArea(.keyboard)  // Compose has own keyboard handler
            .ignoresSafeArea(edges: .all)
		}
	}
}
