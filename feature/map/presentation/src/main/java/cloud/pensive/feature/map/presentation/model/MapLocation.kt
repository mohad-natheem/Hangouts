package cloud.pensive.feature.map.presentation.model

import androidx.compose.runtime.Stable
import com.google.android.gms.maps.model.LatLng
import cloud.pensive.feature.map.domain.model.LocationModel

/**
 * Presentation layer representation of a location
 * Maps domain LocationModel to UI-friendly format with Google Maps LatLng
 * This is UI-specific and not used in domain/data layers
 */
@Stable
data class MapLocation(
    val name: String,
    val latLng: LatLng,
    val zoom: Float = 12F
)

/**
 * Extension to convert domain LocationModel to presentation MapLocation
 */
fun LocationModel.toMapLocation(): MapLocation {
    return MapLocation(
        name = this.name,
        latLng = LatLng(this.latitude, this.longitude),
        zoom = this.zoom
    )
}

