package org.mathieu.cleanrmapi.ui.core.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.mathieu.cleanrmapi.ui.core.ViewModel
import org.mathieu.cleanrmapi.ui.core.navigation.NavigatorProvider
import org.mathieu.cleanrmapi.ui.core.navigation.UiNavigationDestination

@Composable
fun <State, VM: ViewModel<State>> Screen(
    viewModel: VM,
    onEvent: (state: State, viewModel: VM, event: Any) -> Unit = { _, _, _ ->  },
    content: @Composable (state: State, viewModel: VM) -> Unit
) {

    // Observing the state from the view model.
    val state by viewModel.state.collectAsState()

    // Collect events emitted by the ViewModel.
    LaunchedEffect(viewModel) {
        viewModel.events
            .onEach { event ->
                if (event is UiNavigationDestination) NavigatorProvider.navigator.navigateTo(destination = event)
                else onEvent(state, viewModel, event)
            }.collect()
    }

    content(state, viewModel)

}