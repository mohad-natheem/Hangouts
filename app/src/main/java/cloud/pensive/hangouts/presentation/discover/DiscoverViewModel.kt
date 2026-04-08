package cloud.pensive.hangouts.presentation.discover

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Stable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cloud.pensive.hangouts.MainActivity
import cloud.pensive.hangouts.data.remote.OverpassApi
import cloud.pensive.hangouts.domain.model.NearbyPlace
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val locationManager: LocationManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(DiscoverUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<DiscoverUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun updateUserLocationState(location: LatLng) {
        _uiState.update {
            it.copy(userLatLng = location)
        }
    }

    fun updateShowEnableLocationDialogState(show: Boolean) {
        _uiState.update {
            it.copy(showEnableLocationDialog = show)
        }
    }

    fun updateShowEnableLocationInSettingsDialogState(show: Boolean) {
        _uiState.update {
            it.copy(showEnableLocationInSettingsDialog = show)
        }
    }

    fun requestLocationPermission(firsTimePermission: Boolean, context: Context) {
        if (locationManager.hasLocationPermission()) {
            viewModelScope.launch {
                val location = locationManager.getCurrentUserLocation()
                location?.let {
                    updateUserLocationState(
                        LatLng(location.latitude, location.longitude)
                    )
                    sendUiEvent(
                        DiscoverUiEvent.AnimateCameraPosition(
                            LatLng(
                                location.latitude,
                                location.longitude
                            ), 14f
                        )
                    )
                }
            }
        } else {
            if (firsTimePermission) {
                sendUiEvent(DiscoverUiEvent.LaunchLocationActivityResultLauncher)
            } else if (
                locationManager.shouldShowPermissionRationale(context)
            ) {
                updateShowEnableLocationDialogState(true)
            } else {
                updateShowEnableLocationInSettingsDialogState(true)
            }
        }
    }

    fun onAction(action: DiscoverUiAction) {
        when (action) {
            is DiscoverUiAction.OnLocationSelected -> {
                _uiState.update {
                    it.copy(
                        selectedLocation = action.location,
                        isLocationDropdownExpanded = false,
                        locationInput = action.location.name
                    )
                }
                sendUiEvent(
                    DiscoverUiEvent.AnimateCameraPosition(
                        action.location.latLng,
                        action.location.zoom
                    )
                )
            }

            is DiscoverUiAction.OnDropdownExpanded -> {
                _uiState.update {
                    it.copy(isLocationDropdownExpanded = action.expanded)
                }
            }

            is DiscoverUiAction.OnLocationPermissionGranted -> {
                _uiState.update {
                    it.copy(hasLocationPermission = action.granted)
                }
            }

            is DiscoverUiAction.OnUserLocationFetched -> {
                _uiState.update {
                    it.copy(userLatLng = action.latLng, isLoading = false)
                }
                sendUiEvent(
                    DiscoverUiEvent.AnimateCameraPosition(
                        latLng = action.latLng,
                        zoom = 14f
                    )
                )
            }

            is DiscoverUiAction.OnLocationFetchError -> {
                _uiState.update {
                    it.copy(error = action.error, isLoading = false)
                }
            }

            is DiscoverUiAction.UpdateEnableLocationDialogVisibility -> {
                _uiState.update {
                    it.copy(showEnableLocationDialog = action.shouldShow)
                }
            }

            is DiscoverUiAction.UpdateEnableLocationInSettingsDialogVisibility -> {
                _uiState.update {
                    it.copy(showEnableLocationInSettingsDialog = action.shouldShow)
                }
            }
        }
    }

    fun sendUiEvent(event: DiscoverUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun fetchUserLocation(context: Context) {
        viewModelScope.launch {
            try {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    viewModelScope.launch {

                        val fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(context)
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            location?.let {
                                viewModelScope.launch {
                                    val latLng = LatLng(it.latitude, it.longitude)
                                    onAction(DiscoverUiAction.OnUserLocationFetched(latLng))
                                }
                            }
                        }.addOnFailureListener { exception ->
                            onAction(
                                DiscoverUiAction.OnLocationFetchError(
                                    exception.message ?: "Unknown error"
                                )
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                onAction(DiscoverUiAction.OnLocationFetchError(e.message ?: "Unknown error"))
            }
        }
    }

    fun updateLocationInput(input: String) {
        _uiState.update {
            it.copy(
                locationInput = input
            )
        }
    }

    private val api = OverpassApi.create()
    suspend fun searchNearbyPlaces(
        lat: Double,
        lon: Double,
        radiusMeters: Int = 1000,
        keyword: String = ""        // e.g. "restaurant", "hospital", or "" for all
    ): List<NearbyPlace> {
        val filter = if (keyword.isNotBlank()) "[\"name\"~\"$keyword\",i]" else ""
        val query = """
              [out:json][timeout:90];
              (
                node(around:$radiusMeters,$lat,$lon)[name]$filter;
                way(around:$radiusMeters,$lat,$lon)[name]$filter;
              );
              out center 50;
          """.trimIndent()
        return api.searchNearby(query).elements
            .filter { it.lat != null && it.lon != null }
            .map { el ->
                NearbyPlace(
                    id = el.id,
                    name = el.tags?.get("name") ?: "Unknown",
                    lat = el.lat!!,
                    lon = el.lon!!,
                    type = el.tags?.get("amenity")
                        ?: el.tags?.get("shop")
                        ?: el.tags?.get("tourism")
                        ?: "place"
                )
            }
    }

    private val _nearbyPlaces = MutableStateFlow<List<NearbyPlace>>(emptyList())
    val nearbyPlaces = _nearbyPlaces.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    fun searchNearby(lat: Double, lon: Double, keyword: String = "") {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _nearbyPlaces.value = searchNearbyPlaces(lat, lon, keyword = keyword)
                Timber.tag("natheem")
                    .i("Found ${_nearbyPlaces.value.size} places near ($lat, $lon) with keyword '$keyword'")
            } catch (e: Exception) {
                _error.value = "Search failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearPlaces() {
        _nearbyPlaces.value = emptyList()
    }

    @Stable
    data class DiscoverUiState(
        val selectedLocation: MapLocation = predefinedLocations[0],
        val isLocationDropdownExpanded: Boolean = false,
        val hasLocationPermission: Boolean = false,
        val userLatLng: LatLng? = null,
        val isLoading: Boolean = false,
        val error: String? = null,
        val locationInput: String? = null,
        val showEnableLocationDialog: Boolean = false,
        val showEnableLocationInSettingsDialog: Boolean = false,
    )


    sealed interface DiscoverUiAction {
        data class OnLocationSelected(val location: MapLocation) : DiscoverUiAction
        data class OnDropdownExpanded(val expanded: Boolean) : DiscoverUiAction
        data class OnLocationPermissionGranted(val granted: Boolean) : DiscoverUiAction
        data class OnUserLocationFetched(val latLng: LatLng) : DiscoverUiAction
        data class OnLocationFetchError(val error: String) : DiscoverUiAction
        data class UpdateEnableLocationDialogVisibility(val shouldShow: Boolean) : DiscoverUiAction
        data class UpdateEnableLocationInSettingsDialogVisibility(val shouldShow: Boolean) :
            DiscoverUiAction
    }

    sealed interface DiscoverUiEvent {
        data class AnimateCameraPosition(val latLng: LatLng, val zoom: Float) : DiscoverUiEvent

        data object LaunchLocationActivityResultLauncher : DiscoverUiEvent
    }
}