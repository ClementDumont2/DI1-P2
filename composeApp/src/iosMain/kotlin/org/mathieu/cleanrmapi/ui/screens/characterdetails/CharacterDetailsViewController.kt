package org.mathieu.cleanrmapi.ui.screens.characterdetails

import androidx.compose.ui.window.ComposeUIViewController

fun characterDetails(characterId: Int) = ComposeUIViewController {
    CharacterDetailsScreen(id = characterId)
}
