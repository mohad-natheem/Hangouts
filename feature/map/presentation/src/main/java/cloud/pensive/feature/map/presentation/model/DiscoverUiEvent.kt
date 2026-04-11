package cloud.pensive.feature.map.presentation.model

import com.google.android.gms.maps.model.LatLng

/**
 * One-time events emitted by DiscoverViewModel to the UI
 * These are events that should be handled and not persist in state
 */
sealed interface DiscoverUiEvent {
    /**
     * Animate camera to a specific location
     * Used when user selects location or location changes
     */
    data class AnimateCameraPosition(val latLng: LatLng, val zoom: Float) : DiscoverUiEvent

    /**
     * Launch the location permission request dialog
     * Tells the UI to launch the permission launcher
     */
    data object LaunchLocationActivityResultLauncher : DiscoverUiEvent
}

