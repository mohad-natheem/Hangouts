package cloud.pensive.feature.map.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cloud.pensive.core.presentation.commonComposables.CommonDropDown
import cloud.pensive.core.presentation.commonComposables.EnableLocationDialog
import cloud.pensive.core.presentation.commonComposables.EnableLocationInSettingsDialog
import cloud.pensive.core.presentation.commonComposables.TextFieldData
import cloud.pensive.core.presentation.utils.utils.ObserveAsEvents
import cloud.pensive.core.presentation.utils.utils.bottomPadding
import cloud.pensive.core.presentation.utils.utils.endPadding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import cloud.pensive.feature.map.presentation.viewmodel.DiscoverViewModel
import cloud.pensive.feature.map.presentation.model.DiscoverUiAction
import cloud.pensive.feature.map.presentation.model.DiscoverUiEvent
import cloud.pensive.feature.map.presentation.model.DiscoverUiState

@Composable
fun DiscoverScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val markerState = remember {
        MarkerState(position = uiState.selectedLocation.latLng)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            uiState.selectedLocation.latLng,
            uiState.selectedLocation.zoom
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onAction(DiscoverUiAction.OnFetchUserLocation)
        } else {
            viewModel.onAction(
                DiscoverUiAction.OnRequestLocationPermissionClicked(
                    false,
                    context as Activity
                )
            )
        }
    }

    val openSettingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.onAction(DiscoverUiAction.OnRequestLocationPermissionClicked(false, context as Activity))
    }

    // Observe ViewModel events
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is DiscoverUiEvent.AnimateCameraPosition -> {
                coroutineScope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngZoom(
                            event.latLng,
                            event.zoom
                        ),
                        durationMs = 1000
                    )
                    markerState.position = event.latLng
                }
            }

            DiscoverUiEvent.LaunchLocationActivityResultLauncher -> {
                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    // Pass state and action callback to pure screen
    DiscoverScreen(
        modifier = modifier,
        uiState = uiState,
        cameraPositionState = cameraPositionState,
        markerState = markerState,
        onUiAction = viewModel::onAction,
        onUpdateLocationInput = viewModel::updateLocationInput,
        onOpenSettingsClick = {
            val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            openSettingsLauncher.launch(intent)
        },
        onActivityFinish = {
            (context as? Activity)?.finish()
        }
    )
}

/**
 * DiscoverScreen - Pure presentation composable
 *
 * Responsibilities:
 * - Render UI
 * - Collect and emit user actions
 * - Display state changes
 *
 * Does NOT have direct ViewModel access
 * All ViewModel interaction goes through onUiAction callback
 */
@OptIn(ExperimentalMaterial3Api::class)
@Suppress("GoogleMapComposable")
@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    uiState: DiscoverUiState,
    cameraPositionState: com.google.maps.android.compose.CameraPositionState,
    markerState: MarkerState,
    onUiAction: (DiscoverUiAction) -> Unit,
    onUpdateLocationInput: (String) -> Unit,
    onOpenSettingsClick: () -> Unit,
    onActivityFinish: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        GoogleMap(
            onMapLoaded = {
                coroutineScope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngZoom(
                            uiState.selectedLocation.latLng,
                            uiState.selectedLocation.zoom
                        ),
                        durationMs = 1000
                    )
                    markerState.position = uiState.selectedLocation.latLng
                    onUiAction(DiscoverUiAction.OnRequestLocationPermissionClicked(true, context as Activity))
                }
            },
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(),
            uiSettings = MapUiSettings(
                compassEnabled = true,
                zoomControlsEnabled = false,
            )
        ) {
            MarkerComposable(
                state = markerState,
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    tint = Color.Red,
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Selected Location",
                )
            }
        }

        CommonDropDown(
            isDropDownExpanded = uiState.isLocationDropdownExpanded,
            dropDownMenuItems = uiState.predefinedLocations,
            onDropDownMenuItemSelected = { location ->
                onUiAction(DiscoverUiAction.OnLocationSelected(location))
            },
            itemToString = { it.name },
            onExpandedChange = { expanded ->
                onUiAction(DiscoverUiAction.OnDropdownExpanded(expanded))
            },
            textFieldData = TextFieldData(
                textFieldValue = uiState.locationInput ?: "",
                onTextFieldValueChange = onUpdateLocationInput,
                trailingIcon = if (uiState.isLocationDropdownExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down,
                leadingIcon = R.drawable.ic_map_search,
                textFieldPlaceHolder = "Search Area...",
                supportingText = "Select a location to explore"
            )
        )

        // Enable Location Dialog
        if (uiState.showEnableLocationDialog) {
            EnableLocationDialog(
                onDismissRequest = {
                    onUiAction(DiscoverUiAction.UpdateEnableLocationDialogVisibility(false))
                },
                onQuitClick = {
                    onUiAction(DiscoverUiAction.UpdateEnableLocationDialogVisibility(false))
                    onActivityFinish()
                },
                onRequestPermissionClick = {
                    onUiAction(DiscoverUiAction.UpdateEnableLocationDialogVisibility(false))
                    // Note: Permission launcher is triggered via event from ViewModel
                }
            )
        }

        // Enable Location in Settings Dialog
        if (uiState.showEnableLocationInSettingsDialog) {
            EnableLocationInSettingsDialog(
                onDismissRequest = {
                    onUiAction(DiscoverUiAction.UpdateEnableLocationInSettingsDialogVisibility(false))
                },
                onQuitClick = {
                    onUiAction(DiscoverUiAction.UpdateEnableLocationInSettingsDialogVisibility(false))
                    onActivityFinish()
                },
                onOpenSettingsClick = {
                    onUiAction(DiscoverUiAction.UpdateEnableLocationInSettingsDialogVisibility(false))
                    onOpenSettingsClick()
                }
            )
        }

        // Location Action Buttons
        Box(
            modifier = Modifier
                .bottomPadding(60.dp)
                .align(Alignment.BottomEnd)
                .endPadding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val interactionSource = remember { MutableInteractionSource() }

                // My Location FAB
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
                        .padding(18.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onUiAction(DiscoverUiAction.OnFetchUserLocation)
                        },
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_my_location),
                        contentDescription = "My Location"
                    )
                }

                // Add Location FAB (placeholder)
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
                        .padding(18.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            // TODO: Implement add location functionality
                        },
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Add Location"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DropDownPreview() {


}