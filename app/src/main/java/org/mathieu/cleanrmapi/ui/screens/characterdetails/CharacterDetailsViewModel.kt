package org.mathieu.cleanrmapi.ui.screens.characterdetails

import android.app.Application
import android.util.Log
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.models.location.LocationPreview
import org.mathieu.cleanrmapi.domain.repositories.CharacterRepository
import org.mathieu.cleanrmapi.domain.repositories.LocationRepository
import org.mathieu.cleanrmapi.ui.core.ViewModel

class CharacterDetailsViewModel(application: Application) : ViewModel<CharacterDetailsState>(CharacterDetailsState(), application) {

    private val characterRepository: CharacterRepository by inject()
    private val locationRepository: LocationRepository by inject()

    fun init(characterId: Int) {
        fetchData(
            source = { characterRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                updateState { copy(avatarUrl = it.avatarUrl, name = it.name, error = null) }
                loadLocationType(it.locationPreview)
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }

    private fun loadLocationType(locationPreview: Pair<String, Int>) {
        Log.d("VIEWMODEL", "loadLocationType: $locationPreview")
        fetchData(
            source = { locationRepository.getLocationPreview(id = locationPreview.second) }
        ) {
            onSuccess { location ->
                Log.d("VIEWMODEL", "loadLocationType: $location")
                updateState {
                    copy(
                        locationPreview = LocationPreview(
                            id = location.id,
                            name = location.name,
                            type = location.type,
                            dimension = location.dimension
                        )
                    )
                }
            }
            onFailure {
                updateState { copy(error = it.toString()) }
                Log.d("ERR", it.toString())
            }

            updateState { copy(isLoading = false) }
        }
    }

}


data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val locationPreview: LocationPreview = LocationPreview(-1, "", "", ""),
    val error: String? = null
)