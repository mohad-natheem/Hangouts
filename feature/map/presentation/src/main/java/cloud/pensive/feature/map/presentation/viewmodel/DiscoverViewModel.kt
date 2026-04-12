package cloud.pensive.feature.map.presentation.viewmodel

import android.app.Activity
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.util.Result
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import cloud.pensive.feature.map.domain.usecase.GetUserLocationUseCase
import cloud.pensive.feature.map.domain.usecase.CheckLocationPermissionUseCase
import cloud.pensive.feature.map.domain.usecase.GetPredefinedLocationsUseCase
import cloud.pensive.feature.map.domain.util.LocationError
import cloud.pensive.feature.map.presentation.model.DiscoverUiState
import cloud.pensive.feature.map.presentation.model.DiscoverUiAction
import cloud.pensive.feature.map.presentation.model.DiscoverUiEvent
import cloud.pensive.feature.map.presentation.model.DiscoverUiEvent.*
import cloud.pensive.feature.map.presentation.model.toMapLocation
import cloud.pensive.feature.map.presentation.util.PermissionRationaleChecker

/**
 * ViewModel for the Discover screen
 * Manages state and coordinates between use cases and UI
 *
 * KEY CLEAN ARCHITECTURE PRINCIPLES:
 * ✓ No Android Framework imports (except ViewModel)
 * ✓ No Context parameter (location logic handled by use cases)
 * ✓ All location/permission logic delegated to domain use cases
 * ✓ Only handles UI state transitions and event emissions
 * ✓ No direct Android API calls
 *
 * Responsibilities:
 * - Manage UI state (selected location, dropdown, dialogs, loading, errors)
 * - Execute domain use cases and handle their results
 * - Send UI events (camera animations, permission launcher triggers)
 * - Handle UI actions from composables
 */
class DiscoverViewModel(
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val checkLocationPermissionUseCase: CheckLocationPermissionUseCase,
    private val getPredefinedLocationsUseCase: GetPredefinedLocationsUseCase,
    private val permissionRationaleChecker: PermissionRationaleChecker
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiscoverUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<DiscoverUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadPredefinedLocations()
    }

    /**
     * Loads predefined locations for the dropdown
     * Called on ViewModel initialization
     *
     * Data Flow:
     * getPredefinedLocationsUseCase() → domain → repository → data sources
     * Result is mapped to UI models and stored in state
     */
    private fun loadPredefinedLocations() {
        viewModelScope.launch {
            getPredefinedLocationsUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                predefinedLocations = result.data.map { locationModel ->
                                    locationModel.toMapLocation()
                                }
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(error = "Failed to load locations")
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles UI actions from the composable
     * Routes actions to appropriate state updates
     *
     * Each action represents a user interaction and is handled independently
     * No side effects or use case execution here - pure state transitions
     */
    fun onAction(action: DiscoverUiAction) {
        when (action) {
            is DiscoverUiAction.OnLocationSelected -> {
                _uiState.update {
                    it.copy(
                        selectedLocation = action.location,
                        isLocationDropdownExpanded = false,
                        locationInput = TextFieldState(initialText = action.location.name)
                    )
                }
                sendUiEvent(
                    AnimateCameraPosition(
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

            is DiscoverUiAction.OnFetchUserLocation -> {
                fetchUserLocation()
            }

            is DiscoverUiAction.OnRequestLocationPermissionClicked -> {
                requestLocationPermission(action.isFirstTime, action.activity)
            }

            is DiscoverUiAction.OnCreateEventFABClicked -> {
                _uiState.update {
                    it.copy(
                        showCreateEventBottomSheet = action.shouldShow
                    )
                }
            }
        }
    }


    /**
     * Sends a one-time UI event
     * Used for one-off events that shouldn't be persisted in state
     */
    private fun sendUiEvent(event: DiscoverUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    /**
     * Initiates location permission request flow
     *
     * CLEAN ARCHITECTURE: All permission logic is in domain layer
     * This method ONLY orchestrates use cases and state transitions
     *
     * Flow:
     * 1. Check if permission already granted (use case)
     *    - If yes: initiate location fetch
     *    - If no: proceed to step 2
     * 2. Determine which dialog to show based on permission state
     *    - First time: show system permission launcher
     *    - Has rationale: show explanation dialog
     *    - No rationale: show settings navigation dialog
     *
     * @param isFirstTimePermission - true if first time requesting permission
     */
    fun requestLocationPermission(isFirstTimePermission: Boolean, activity: Activity) {
        viewModelScope.launch {
            // Step 1: Check if permission already granted via use case
            val hasPermissionResult = checkLocationPermissionUseCase()

            when (hasPermissionResult) {
                is Result.Error -> {
                    _uiState.update {
                        it.copy(error = "Failed to check permission")
                    }
                    return@launch
                }

                is Result.Success -> {
                    if (hasPermissionResult.data) {
                        // Permission already granted - proceed to fetch location
                        fetchUserLocation()
                        return@launch
                    }
                }
            }

            // Step 2: Permission not granted - determine which dialog/action to show
            if (isFirstTimePermission) {
                // First time requesting - launch system permission request
                sendUiEvent(DiscoverUiEvent.LaunchLocationActivityResultLauncher)
            } else {
                // Not first time - check if should show rationale explanation via use case
                val shouldShowRationaleResult =
                    permissionRationaleChecker.shouldShowLocationPermissionRationale(activity)

                when (shouldShowRationaleResult) {
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(error = "Failed to check permission rationale")
                        }
                    }

                    is Result.Success -> {
                        if (shouldShowRationaleResult.data) {
                            // Show explanation dialog with rationale
                            _uiState.update {
                                it.copy(showEnableLocationDialog = true)
                            }
                        } else {
                            // User selected "Don't ask again" - show settings navigation dialog
                            _uiState.update {
                                it.copy(showEnableLocationInSettingsDialog = true)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Fetches current user location
     *
     * CLEAN ARCHITECTURE:
     * ✓ No direct use of FusedLocationProviderClient
     * ✓ No permission checks here (handled by use case)
     * ✓ Only orchestrates GetUserLocationUseCase and handles results
     *
     * The use case itself includes permission checks and returns appropriate errors
     * This method only handles state updates and error message formatting
     */
    fun fetchUserLocation() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Execute use case which handles all location fetching logic
            getUserLocationUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        val latLng = LatLng(result.data.latitude, result.data.longitude)
                        _uiState.update {
                            it.copy(userLatLng = latLng, isLoading = false)
                        }
                        sendUiEvent(
                            DiscoverUiEvent.AnimateCameraPosition(
                                latLng = latLng,
                                zoom = 14f
                            )
                        )
                    }

                    is Result.Error -> {
                        val errorMessage = when (result.error) {
                            LocationError.NoPermission -> "Location permission required"
                            LocationError.ServiceUnavailable -> "Location service unavailable"
                            LocationError.Timeout -> "Location request timed out"
                            LocationError.Unknown -> "Unknown error occurred"
                        }
                        _uiState.update {
                            it.copy(error = errorMessage, isLoading = false)
                        }
                    }
                }
            }
        }
    }
}

