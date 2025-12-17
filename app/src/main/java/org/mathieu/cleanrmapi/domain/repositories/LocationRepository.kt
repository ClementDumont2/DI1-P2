package org.mathieu.cleanrmapi.domain.repositories
import org.mathieu.cleanrmapi.domain.models.location.Location
import org.mathieu.cleanrmapi.domain.models.location.LocationPreview

interface LocationRepository {
    suspend fun getLocationPreview(id: Int): LocationPreview
    suspend fun getLocation(id: Int): Location
}