package cloud.pensive.feature.map.presentation.model

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import com.google.android.gms.maps.model.LatLng

/**
 * UI State for the Discover screen
 * Represents all observable data the screen needs
 */
@Stable
data class DiscoverUiState(
    // Location selection
    val selectedLocation: MapLocation = MapLocation(
        name = "New York",
        latLng = LatLng(40.7128, -74.0060)
    ),
    val isLocationDropdownExpanded: Boolean = false,
    val locationInput: TextFieldState = TextFieldState(),

    // User's current location
    val userLatLng: LatLng? = null,
    val hasLocationPermission: Boolean = false,

    // Permission dialogs
    val showEnableLocationDialog: Boolean = false,
    val showEnableLocationInSettingsDialog: Boolean = false,

    // Loading and error states
    val isLoading: Boolean = false,
    val error: String? = null,

    // Available locations for dropdown
    val predefinedLocations: List<MapLocation> = emptyList()
)

