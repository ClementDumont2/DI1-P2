import SwiftUI
import DreemNav
import ComposeApp

class NavigatorImpl: Navigator {
    
    private let navController: NavController
    
    init(navController: NavController) {
        self.navController = navController
    }
    
    func navigateTo(destination: UiNavigationDestination, clearBackStack: Bool = false) {
        if destination is UiNavigationDestinationPopBack {
            if destination.route.isEmpty {
                navController.pop(to: nil, arguments: destination.arguments)
            } else {
                navController.pop(to: destination.route, arguments: destination.arguments)
            }
        } else {
            navController.push(screenName: destination.route, arguments: destination.arguments, transition: .coverHorizontal, asNewRoot: clearBackStack)
        }
        
    }
    
    func pushDialog(@ViewBuilder content: () -> some View) {
        navController.push(screenName: "dialog\(Date().description)", transition: .dialog, content: content)
    }
    
    func dismissLastDialog() {
        navController.pop()
    }
    
}
