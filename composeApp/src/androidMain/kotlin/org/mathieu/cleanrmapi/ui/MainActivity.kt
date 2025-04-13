package org.mathieu.cleanrmapi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinContext
import org.mathieu.cleanrmapi.ui.core.extensions.navigation.composable
import org.mathieu.cleanrmapi.ui.core.navigation.CharacterDestination.CharacterDetails
import org.mathieu.cleanrmapi.ui.core.navigation.CharacterDestination.Characters
import org.mathieu.cleanrmapi.ui.core.navigation.EpisodeDestination.EpisodeDetails
import org.mathieu.cleanrmapi.ui.core.navigation.NavigatorImpl
import org.mathieu.cleanrmapi.ui.core.navigation.loadNavigationModule
import org.mathieu.cleanrmapi.ui.screens.characterdetails.CharacterDetailsScreen
import org.mathieu.cleanrmapi.ui.screens.characters.CharactersScreen
import org.mathieu.cleanrmapi.ui.screens.episodedetails.EpisodeDetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            App()

        }
    }
}

@Composable
private fun App() {
    KoinContext {
        MainContent()
    }
}

@Composable
private fun MainContent() {

    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        loadNavigationModule(
            NavigatorImpl(navController)
        )
    }

    //https://developer.android.com/jetpack/compose/navigation?hl=fr
    NavHost(navController = navController, startDestination = "characters") {

        composable(Characters) { CharactersScreen() }

        composable(
            destination = CharacterDetails()
        ) { backStackEntry ->

            CharacterDetailsScreen(
                id = backStackEntry.arguments?.getInt("characterId") ?: -1
            )

        }

        composable(
            destination = EpisodeDetails()
        ) { backStackEntry ->

            EpisodeDetailsScreen(
                id = backStackEntry.arguments?.getInt("episodeId") ?: -1
            )

        }

    }

}
