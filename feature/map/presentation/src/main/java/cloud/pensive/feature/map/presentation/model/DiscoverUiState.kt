package cloud.pensive.feature.map.presentation.model

import android.graphics.drawable.Icon
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import cloud.pensive.core.presentation.ArrowRightIcon
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

    // Permission dialogs
    val showEnableLocationDialog: Boolean = false,
    val showEnableLocationInSettingsDialog: Boolean = false,

    // Loading and error states
    val isLoading: Boolean = false,
    val error: String? = null,

    // Available locations for dropdown
    val predefinedLocations: List<MapLocation> = emptyList(),

    val showCreateEventBottomSheet: Boolean = false,
    val createEventState: CreateEventState = CreateEventState()
)

data class CreateEventState(
    val selectedEventType: EventType = EventType.NATIVE,
    val eventNameTextFieldState: TextFieldState = TextFieldState(),
    val eventDescriptionTextFieldState: TextFieldState = TextFieldState(),
    val maxMemberTextFieldState: TextFieldState = TextFieldState(),
    val eventLocation: LatLng? = null,
    val selectedCategory: EventCategory = EventCategory.ART
)

enum class EventType(val value: String) {
    NATIVE("Native"), EXTERNAL("External")
}


enum class EventCategory(val value: String) {
    ART("Art"), BOOKS("Books"), FITNESS("Fitness"), FOOD("Food"), OTHER("Other")
}
