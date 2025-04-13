import SwiftUI
import ComposeApp
import DreemNav

@main
struct iOSApp: App {
    @ObservedObject
    private var navController: NavController
    
    init() {
        
        navController = NavController(root: CharacterDestination.Characters().route) {
            $0.screen(CharacterDestination.Characters().route) { _ in
                CharactersScreen3()
//                CharactersViewControllerKt
//                    .characters()
//                    .toRepresentable()
            }
            
            $0.screen(CharacterDestination.CharacterDetails().route) { params in
                if let characterId = params["characterId"] as? Int32 {
                    CharacterDetailsViewControllerKt
                        .characterDetails(characterId: characterId)
                        .toRepresentable()
                }
            }
            
            $0.screen(EpisodeDestination.EpisodeDetails().route) { params in
                if let episodeId = params["episodeId"] as? Int32 {
                    EpisodeDetailsViewControllerKt
                        .episodeDetails(episodeId: episodeId)
                        .toRepresentable()
                }
            }

        }
        
        KoinInitializerKt.doInitKoin()
        NavigatorKt.loadNavigationModule(navigator: NavigatorImpl(navController: navController))
    }
    
    var body: some Scene {
        WindowGroup {
            NavHost(controller: navController, backgroundColor: .white)
        }
    }
}

extension UIViewController {
    func toRepresentable() -> some View {
        UIViewControllerWrapper(controller: self)
    }
}

private struct UIViewControllerWrapper: UIViewControllerRepresentable {
    let controller: UIViewController
    
    func makeUIViewController(context: Context) -> UIViewController {
        controller
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // No-op
    }
}
