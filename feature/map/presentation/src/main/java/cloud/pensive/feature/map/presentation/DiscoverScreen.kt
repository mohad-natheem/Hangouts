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
import androidx.compose.runtime.LaunchedEffect
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
import cloud.pensive.core.presentation.utils.utils.bottomPadding
import cloud.pensive.core.presentation.utils.utils.endPadding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

data class MapLocation(
    val name: String,
    val latLng: LatLng,
    val zoom: Float = 12F
)

val predefinedLocations = listOf(
    MapLocation("New York", LatLng(40.7128, -74.0060)),
    MapLocation("London", LatLng(51.5074, -0.1278))
)

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("GoogleMapComposable")
@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            uiState.selectedLocation.latLng,
            uiState.selectedLocation.zoom
        )
    }

    val context = LocalContext.current

    val markerState = remember {
        MarkerState(position = uiState.selectedLocation.latLng)
    }

    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.fetchUserLocation(context)
        } else {
            //Show alert
            viewModel.requestLocationPermission(false, context)
        }
    }

    val openSettingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.requestLocationPermission(false, context)
    }

//    fun requestLocationPermission(firsTimePermission: Boolean) {
//        val hasPermission = ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//        if (hasPermission) {
//            viewModel.fetchUserLocation(context)
//        } else {
//            if (firsTimePermission) {
//                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            } else if (
//                ActivityCompat.shouldShowRequestPermissionRationale(
//                    context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            ) {
//                viewModel.onAction(
//                    DiscoverViewModel.DiscoverUiAction.UpdateEnableLocationDialogVisibility(
//                        true
//                    )
//                )
//                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            } else {
//                viewModel.onAction(
//                    DiscoverViewModel.DiscoverUiAction.UpdateEnableLocationInSettingsDialogVisibility(
//                        true
//                    )
//                )
//            }
//        }
//    }


    // Animate camera when location selection changes
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is DiscoverViewModel.DiscoverUiEvent.AnimateCameraPosition -> {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngZoom(
                            event.latLng,
                            event.zoom
                        ),
                        durationMs = 1000
                    )
                    markerState.position = event.latLng
                }

                DiscoverViewModel.DiscoverUiEvent.LaunchLocationActivityResultLauncher -> {
                    launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }

        }
    }

    if (uiState.showEnableLocationDialog) {
        EnableLocationDialog(
            onDismissRequest = {
                viewModel.onAction(
                    DiscoverViewModel.DiscoverUiAction.UpdateEnableLocationDialogVisibility(
                        false
                    )
                )
            },
            onQuitClick = {
                viewModel.onAction(
                    DiscoverViewModel.DiscoverUiAction.UpdateEnableLocationDialogVisibility(
                        false
                    )
                )
                (context as? Activity)?.finish()
            },
            onRequestPermissionClick = {
                viewModel.onAction(
                    DiscoverViewModel.DiscoverUiAction.UpdateEnableLocationDialogVisibility(
                        false
                    )
                )
                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        )
    }

    if (uiState.showEnableLocationInSettingsDialog) {
        EnableLocationInSettingsDialog(
            onDismissRequest = {
                viewModel.onAction(
                    DiscoverViewModel.DiscoverUiAction.UpdateEnableLocationInSettingsDialogVisibility(
                        false
                    )
                )
            },
            onQuitClick = {
                viewModel.onAction(
                    DiscoverViewModel.DiscoverUiAction.UpdateEnableLocationInSettingsDialogVisibility(
                        false
                    )
                )
                (context as? Activity)?.finish()
            },
            onOpenSettingsClick = {
                viewModel.onAction(
                    DiscoverViewModel.DiscoverUiAction.UpdateEnableLocationInSettingsDialogVisibility(
                        false
                    )
                )
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }

                openSettingsLauncher.launch(intent)
            }
        )
    }

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
                    viewModel.requestLocationPermission(true, context)
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
            dropDownMenuItems = predefinedLocations,
            onDropDownMenuItemSelected = { location ->
                viewModel.onAction(
                    DiscoverViewModel.DiscoverUiAction.OnLocationSelected(location)
                )

            },
            itemToString = { it.name },
            onExpandedChange = { expanded ->
                viewModel.onAction(DiscoverViewModel.DiscoverUiAction.OnDropdownExpanded(expanded))
            },
            textFieldData = TextFieldData(
                textFieldValue = uiState.locationInput ?: "",
                onTextFieldValueChange = viewModel::updateLocationInput,
                trailingIcon = if (uiState.isLocationDropdownExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down,
                leadingIcon = R.drawable.ic_map_search,
                textFieldPlaceHolder = "Search Area...",
                supportingText = "Select a location to explore"
            )
        )

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

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
                        .padding(18.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            viewModel.fetchUserLocation(context)
                        },
                ) {

                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_my_location),
                        contentDescription = "My Location"
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
                        .padding(18.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
//                            viewModel.searchNearby(
//                                uiState.userLatLng?.latitude!!,
//                                uiState.userLatLng?.longitude!!
//                            )
                        },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "My Location"
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