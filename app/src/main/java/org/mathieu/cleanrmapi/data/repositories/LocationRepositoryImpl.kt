package org.mathieu.cleanrmapi.data.repositories
import org.mathieu.cleanrmapi.data.local.LocationLocal
import org.mathieu.cleanrmapi.data.local.objects.toPreviewModel
import org.mathieu.cleanrmapi.data.local.objects.toRealmObject
import org.mathieu.cleanrmapi.data.remote.LocationApi
import org.mathieu.cleanrmapi.domain.models.location.Location
import org.mathieu.cleanrmapi.domain.models.location.LocationPreview
import org.mathieu.cleanrmapi.domain.repositories.LocationRepository
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository

internal class LocationRepositoryImpl(
    private val locationApi: LocationApi,
    private val locationLocal: LocationLocal,
    private val characterRepo: CharacterRepository
) : LocationRepository {
    override suspend fun getLocationPreview(id: Int): LocationPreview =
        locationLocal.getLocation(id)?.toPreviewModel()
            ?: locationApi.getLocation(id = id)?.let { response ->
                val obj = response.toRealmObject()
                locationLocal.insert(obj)
                obj.toPreviewModel()
            }
            ?: throw Exception("Location not found.")

    override suspend fun getLocation(id: Int): Location {
        val response = locationApi.getLocation(id)
            ?: throw Exception("Location not found.")
        val charactersIds = response.residents.mapNotNull {
            it.substringAfterLast("/").toIntOrNull() }
        val characters = characterRepo
            .getCharactersByIds(charactersIds)


        return Location(
            id = response.id,
            name = response.name,
            type = response.type,
            dimension = response.dimension,
            residents = characters
        )
    }
}