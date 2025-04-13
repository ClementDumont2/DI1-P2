package org.mathieu.cleanrmapi.ui.core.extensions.navigation

import android.net.Uri
import android.os.Parcelable
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.NavType.Companion.BoolArrayType
import androidx.navigation.NavType.Companion.BoolType
import androidx.navigation.NavType.Companion.FloatArrayType
import androidx.navigation.NavType.Companion.FloatType
import androidx.navigation.NavType.Companion.IntArrayType
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.NavType.Companion.LongArrayType
import androidx.navigation.NavType.Companion.LongType
import androidx.navigation.NavType.Companion.StringArrayType
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.NavType.EnumType
import androidx.navigation.NavType.ParcelableArrayType
import androidx.navigation.NavType.ParcelableType
import androidx.navigation.NavType.SerializableArrayType
import androidx.navigation.NavType.SerializableType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.mathieu.cleanrmapi.ui.core.navigation.UiNavigationDestination
import java.io.Serializable

/**
 * Registers a composable destination in the navigation graph using a [UiNavigationDestination].
 *
 * This function extracts the route and arguments from the [UiNavigationDestination] and prepares
 * them for use in the NavGraphBuilder DSL.
 *
 * The route will use placeholder values for arguments (e.g., `{key}`) because the function calls `toUriRoute(false)`,
 * which builds the URI template, not the actual navigation path.
 *
 * @param destination The destination to register.
 * @param content The composable screen associated with the destination.
 */
fun NavGraphBuilder.composable(destination: UiNavigationDestination, content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit) =
    with(destination) {
        composable(
            route = toUriRoute(),
            arguments = arguments.keys.map { key ->
                navArgument(key) {
                    type = inferFromValueType(arguments[key])
                    nullable = type == StringType
                }
            },
            content = content
        )
    }

@Suppress("UNCHECKED_CAST")
private fun inferFromValueType(value: Any?): NavType<Any> {
    return when {
        value is Int -> IntType as NavType<Any>
        value is IntArray -> IntArrayType as NavType<Any>
        value is Long -> LongType as NavType<Any>
        value is LongArray -> LongArrayType as NavType<Any>
        value is Float -> FloatType as NavType<Any>
        value is FloatArray -> FloatArrayType as NavType<Any>
        value is Boolean -> BoolType as NavType<Any>
        value is BooleanArray -> BoolArrayType as NavType<Any>
        value is String || value == null -> StringType as NavType<Any>
        value is Array<*> && value.isArrayOf<String>() -> StringArrayType as NavType<Any>
        value.javaClass.isArray &&
                Parcelable::class.java.isAssignableFrom(value.javaClass.componentType!!) -> {
            ParcelableArrayType(value.javaClass.componentType as Class<Parcelable>)
                    as NavType<Any>
        }
        value.javaClass.isArray &&
                java.io.Serializable::class.java.isAssignableFrom(value.javaClass.componentType!!) -> {
            SerializableArrayType(value.javaClass.componentType as Class<java.io.Serializable>)
                    as NavType<Any>
        }
        value is Parcelable -> ParcelableType(value.javaClass) as NavType<Any>
        value is Enum<*> -> EnumType(value.javaClass) as NavType<Any>
        value is Serializable -> SerializableType(value.javaClass) as NavType<Any>
        else -> {
            throw IllegalArgumentException(
                "Object of type ${value.javaClass.name} is not supported for navigation " +
                        "arguments."
            )
        }
    }
}

/**
 * Builds the route URI for the destination.
 *
 * @param isActualNavigation If `true`, generates a fully resolved URI (e.g., `characters/details?characterId=42`)
 *                           for use in navigation via `NavController.navigate(...)`.
 *                           If `false`, generates a placeholder-based URI (e.g., `characters/details?characterId={characterId}`)
 *                           for use when declaring the route in the NavGraph.
 *
 * @return A string representing the URI for either graph declaration or actual navigation.
 */
fun UiNavigationDestination.toUriRoute(isActualNavigation: Boolean = false): String {
    var uri = route
    if (arguments.isNotEmpty()) {
        var first = true
        arguments.forEach { (key, value) ->
            val query = if (isActualNavigation) "$key=${Uri.encode(value.toString())}" else "$key={$key}"
            if (first) {
                uri += "?"
                first = false
            } else {
                uri += "&"
            }
            uri += query
        }
    }
    return uri
}
