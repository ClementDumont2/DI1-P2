//
//  TestScreen.swift
//  iosApp
//
//  Created by Mathieu Perroud on 13/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp

struct CharactersScreen3: View {
    
    var viewModel = CharactersViewModel()
    
    var body: some View {
        // ViewModel is in Kotlin and there is a navigation event that is catch into Screen when we click on a character name.
        // Navigation is handled.
        Screen(
            viewModel: CharactersViewModel()
        ) { uiState, viewModel in
            Content(
                uiState: uiState,
                onAction: { viewModel.handleAction(action: $0) }
            )
        }
        
    }
    
}

@ViewBuilder
private func Content(
    uiState: CharactersContractsUiState,
    onAction: @escaping (CharactersContractsUiAction) -> Void
) -> some View {
    ScrollView {
        VStack {
            
            ForEach(uiState.characters, id: \.id) { character in
                Text(character.name)
                    .foregroundColor(.black)
                    .onTapGesture {
                        onAction(
                            CharactersContractsSelectedCharacter(character: character)
                        )
                    }
            }
            
        }
    }
}

