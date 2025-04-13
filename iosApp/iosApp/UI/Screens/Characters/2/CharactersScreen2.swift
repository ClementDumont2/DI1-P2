//
//  TestScreen.swift
//  iosApp
//
//  Created by Mathieu Perroud on 13/04/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp

struct CharactersScreen2: View {
    @StateObject
    var viewModel = CharactersViewModel2()
    let navigator: Navigator = get()
    
    var body: some View {
        
        ScrollView {
            VStack {
                
                ForEach(viewModel.characters, id: \.id) { character in
                    Text(character.name)
                        .foregroundColor(.black)
                        .onTapGesture {
                            navigator.navigateTo(
                                destination: CharacterDestination
                                    .CharacterDetails(
                                        characterId: character.id
                                    ),
                                clearBackStack: false
                            )
                        }
                }
                
            }
        }
        
    }
}

