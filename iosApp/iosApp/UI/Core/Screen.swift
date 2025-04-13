import SwiftUI
import ComposeApp

struct Screen<Content: View, ViewModel: ComposeApp.ViewModel<UiState>, UiState>: View  {
    
    private let onEvent: ((UiState, ViewModel, Any) -> Void)?
    
    @ViewBuilder
    private let content: (UiState, ViewModel) -> Content
    
    private let viewModel: ViewModel
    private let navigator: Navigator = NavigatorProvider().navigator
    
    @State
    private var state: UiState
    private var disposeBag = DisposeBag()
    
    init(
        viewModel: ViewModel,
        onEvent: ((UiState, ViewModel, Any) -> Void)? = nil,
        @ViewBuilder content: @escaping (UiState, ViewModel) -> Content
    ) {
        self.viewModel = viewModel
        self.onEvent = onEvent
        self.content = content
        self.state = viewModel.state.value as! UiState
    }
    
    var body: some View {
        self.content(state, viewModel)
            .onAppear {
                self.viewModel.state.collect { (state: UiState) in
                    self.state = state
                }.add(to: disposeBag)
                self.viewModel.events.collect { (event: Any) in
                    switch event {
                    case let navigationEvent as UiNavigationDestination:
                            navigator.navigateTo(destination: navigationEvent, clearBackStack: false)
                    default:
                        onEvent?(state, viewModel, event)
                    }
                }.add(to: disposeBag)
            }
            .onDisappear {
                disposeBag.dispose()
            }
            .onTapGesture {
                UIApplication
                    .shared
                    .sendAction(
                        #selector(UIResponder.resignFirstResponder),
                        to: nil,
                        from: nil,
                        for: nil
                    )
            }
    }
}

