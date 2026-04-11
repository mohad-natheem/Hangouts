package cloud.pensive.feature.map.data.source

import kotlinx.coroutines.flow.Flow
import com.plcoding.core.domain.util.Result
import cloud.pensive.feature.map.domain.model.LocationModel
import cloud.pensive.feature.map.domain.model.UserLocation
import cloud.pensive.feature.map.domain.util.LocationError

/**
 * Abstract interface for location data access
 * Allows different implementations (remote device GPS, local cache, API, etc.)
 */
interface LocationDataSource {

    fun getCurrentUserLocation(): Flow<Result<UserLocation, LocationError>>

    fun getPredefinedLocations(): Flow<Result<List<LocationModel>, LocationError>>
}

