package org.mathieu.cleanrmapi.data.local

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.mathieu.cleanrmapi.data.local.objects.LocationObject

internal class LocationLocal(private val database: RealmDatabase) {


    suspend fun getLocation(id: Int): LocationObject? = database.use {
        query<LocationObject>("id == $id").first().find()
    }

    suspend fun saveLocations(locations: List<LocationObject>) = locations.onEach {
        insert(it)
    }

    suspend fun insert(location: LocationObject) {
        database.write {
            copyToRealm(location, UpdatePolicy.ALL)
        }
    }
}