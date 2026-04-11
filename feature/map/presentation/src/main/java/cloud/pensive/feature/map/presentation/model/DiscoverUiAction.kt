package cloud.pensive.feature.map.presentation.model

import android.app.Activity

/**
 * User actions triggered on the Discover screen
 * These represent user interactions with the UI
 */
sealed interface DiscoverUiAction {
    /**
     * User selected a location from the dropdown
     */
    data class OnLocationSelected(val location: MapLocation) : DiscoverUiAction

    /**
     * User toggled the location dropdown open/closed
     */
    data class OnDropdownExpanded(val expanded: Boolean) : DiscoverUiAction


    /**
     * Update visibility of "enable location" dialog
     */
    data class UpdateEnableLocationDialogVisibility(val shouldShow: Boolean) : DiscoverUiAction

    /**
     * Update visibility of "enable location in settings" dialog
     */
    data class UpdateEnableLocationInSettingsDialogVisibility(val shouldShow: Boolean) :
        DiscoverUiAction

    /**
     * User clicked the "Get My Location" button
     */
    data object OnFetchUserLocation : DiscoverUiAction

    /**
     * User requested location permission
     * @param isFirstTime - true if first time requesting, false otherwise
     */
    data class OnRequestLocationPermissionClicked(val isFirstTime: Boolean, val activity: Activity) : DiscoverUiAction
}

