package org.mathieu.cleanrmapi.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Transforms each element in a list within a Flow.
 *
 * This extension function on Flow<List<T>> applies a transformation function to each element
 * of each list emitted by the original Flow, producing a new Flow that emits lists of the
 * transformed elements.
 *
 * The transformation is defined by the [transform] parameter, a suspending function that
 * takes an element of type [T] and returns a new element of type [R]. This allows for
 * asynchronous transformations of each element, such as fetching data from a database or
 * a network call.
 *
 * Example usage:
 * ```
 * val originalFlow: Flow<List<Int>> = flowOf(listOf(1, 2, 3))
 * val transformedFlow: Flow<List<String>> = originalFlow.mapElement { it.toString() }
 * ```
 *
 * @param transform A suspending transformation function to apply to each element of each list.
 * @return A new Flow emitting lists of transformed elements.
 */
inline fun <T, R> Flow<List<T>>.mapElement(crossinline transform: suspend (value: T) -> R): Flow<List<R>> =
    this.map { list ->
        list.map { element -> transform(element) }
    }

fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)

/**
 * In order to permit Swift interop, we've made a custom flow wrapper that can expose wrapped values of flows
 */
class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin