package org.mathieu.cleanrmapi.ui.screens.locationdetails

import android.app.Application
import android.util.Log
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.models.location.Location
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import org.mathieu.cleanrmapi.domain.repositories.LocationRepository
import org.mathieu.cleanrmapi.ui.core.ViewModel
import kotlin.getValue

class LocationDetailsViewModel(application: Application) : ViewModel<LocationDetailsState>(LocationDetailsState(), application) {

    private val characterRepository: CharacterRepository by inject()
    private val locationRepository: LocationRepository by inject()

    fun init(locationId: Int) {
        fetchData(
            source = { locationRepository.getLocation(id = locationId) }
        ) {

            onSuccess { location ->
                updateState { copy(location = location, error = null) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }

}


public final data class LocationDetailsState(
    public final val isLoading: Boolean = true,
    public final val location: Location = Location(-1, "", "", "", emptyList()),
    public final val error: String? = null
)
