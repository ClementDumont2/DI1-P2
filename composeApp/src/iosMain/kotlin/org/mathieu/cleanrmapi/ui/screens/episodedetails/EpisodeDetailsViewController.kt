package org.mathieu.cleanrmapi.ui.screens.episodedetails

import androidx.compose.ui.window.ComposeUIViewController

fun episodeDetails(episodeId: Int) = ComposeUIViewController {
    EpisodeDetailsScreen(id = episodeId)
}
