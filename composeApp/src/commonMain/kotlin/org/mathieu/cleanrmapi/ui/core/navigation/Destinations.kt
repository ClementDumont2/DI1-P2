package org.mathieu.cleanrmapi.ui.core.navigation

import org.mathieu.cleanrmapi.ui.core.navigation.EpisodeDestination.EpisodeDetails
import org.mathieu.cleanrmapi.ui.core.navigation.UiNavigationDestination.NavigateTo

sealed interface UiNavigationDestination {
    val route: String
    val arguments: Map<String, Any>
    sealed interface NavigateTo: UiNavigationDestination


    class PopBack(destination: UiNavigationDestination? = null, val inclusive: Boolean = false, override val arguments: Map<String, Any> = mapOf()) : UiNavigationDestination {
        override val route: String = destination?.route ?: ""
    }
}

sealed class CharacterDestination(
    override val route: String,
    override val arguments: Map<String, Any> = emptyMap()
): NavigateTo {

    data object Characters: CharacterDestination(route = "characters")

    /**
     * Represents the navigation destination for the character details screen.
     *
     * This destination is defined with a fixed route (`"characters/details"`) and an optional argument
     * [characterId] used for dynamic navigation.
     *
     * The default value for [characterId] allows us to instantiate this data class
     * when building the NavGraph without needing a real value.
     *
     * Note: When registering the destination in the navigation graph (e.g., in `NavGraphBuilder.composable`),
     * the [characterId] value is ignored and its key is instead used to build the placeholder in the route
     * (e.g., `characters/details?characterId={characterId}`).
     *
     * When navigating **to** this destination (e.g., via `Navigator.navigateTo(destination)`),
     * the actual [characterId] value is used to construct the full query string (e.g., `characters/details?characterId=42`).
     */
    data class CharacterDetails(
        val characterId: Int
    ):
        CharacterDestination(
            route = "characters/details",
            arguments = mapOf("characterId" to characterId)
        ) { constructor() : this(0) }
}


sealed class EpisodeDestination(
    override val route: String,
    override val arguments: Map<String, Any> = emptyMap()
): NavigateTo {

    /**
     * @see CharacterDestination.Characters explanations
     */
    data class EpisodeDetails(
        val episodeId: Int
    ):
        EpisodeDestination(
            route = "episodes/details",
            arguments = mapOf("episodeId" to episodeId)
        ) { constructor() : this(0) }
}
