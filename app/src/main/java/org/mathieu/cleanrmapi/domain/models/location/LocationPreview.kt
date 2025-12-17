package org.mathieu.cleanrmapi.domain.models.location

/**
 * Light version of location, that represents a specific location within a universe or dimension.
 *
 * @property id The unique identifier for the location.
 * @property name The name of the location.
 * @property type The type or category of the location.
 * @property dimension The specific dimension or universe where this location exists.
 */
data class LocationPreview(
    val id: Int,
    var name: String,
    var type: String,
    var dimension: String
)
