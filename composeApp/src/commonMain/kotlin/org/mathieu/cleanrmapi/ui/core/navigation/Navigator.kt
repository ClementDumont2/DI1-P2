package org.mathieu.cleanrmapi.ui.core.navigation

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun loadNavigationModule(navigator: Navigator) {
    loadKoinModules(
        module {
            single<Navigator> { navigator }
        }
    )
}

object NavigatorProvider: KoinComponent {
    val navigator: Navigator by inject()
}

interface Navigator {
    fun navigateTo(destination: UiNavigationDestination, clearBackStack: Boolean = false)
}

fun Navigator.popBack(destination: UiNavigationDestination? = null, inclusive: Boolean = false, arguments: Map<String, Any> = mapOf()) {
    navigateTo(UiNavigationDestination.PopBack(destination, inclusive, arguments))
}
