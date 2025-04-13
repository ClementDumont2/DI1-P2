//
//  TestViewModel.swift
//  iosApp
//
//  Created by Mathieu Perroud on 13/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import ComposeApp

class CharactersViewModel2: ObservableObject {
    
    @Published var characters: [Character] = []
    
    let charactersRepository: CharacterRepository = get()
    
    init() {
        
        collectData(
            source: {
                try await self.charactersRepository.getCharacters()
            },
            onResult: {
            
                $0.onSuccess { [weak self] in
                    guard let characters = $0 as? [Character] else { return }
                    self?.characters = characters
                }
                
                $0.onFailure { error in
                    
                }
                
            }
        )
    
    }
    
}

