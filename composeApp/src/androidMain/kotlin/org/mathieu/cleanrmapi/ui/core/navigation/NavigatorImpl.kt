package org.mathieu.cleanrmapi.ui.core.navigation

import androidx.navigation.NavController
import org.mathieu.cleanrmapi.ui.core.extensions.navigation.toUriRoute


class NavigatorImpl(private val navController: NavController): Navigator {

    override fun navigateTo(destination: UiNavigationDestination, clearBackStack: Boolean) {
        when (destination) {
            is UiNavigationDestination.PopBack -> navController.pop(destination)
            is UiNavigationDestination.NavigateTo -> navController.navigateTo(destination, clearBackStack)
        }
    }

}



private fun NavController.navigateTo(destination: UiNavigationDestination, clearBackStack: Boolean) {
    navigate(destination.toUriRoute(isActualNavigation = true)) {
        if (clearBackStack) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }
}

private fun NavController.pop(destination: UiNavigationDestination.PopBack) {
    val entry = if (destination.route.isEmpty()) // means there is no specific destination to back, we just want to pop the last screen
        previousBackStackEntry
    else
        getBackStackEntry(destination.route)

    entry?.savedStateHandle?.apply {
        destination.arguments.forEach { (key, value) ->
            set(key, value)
        }
    }

    if (destination.route.isEmpty())
        popBackStack()
    else
        popBackStack(destination.route, destination.inclusive)
}
