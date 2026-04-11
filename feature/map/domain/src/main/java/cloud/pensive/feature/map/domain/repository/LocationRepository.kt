package cloud.pensive.feature.map.domain.repository

import com.plcoding.core.domain.util.Result
import cloud.pensive.feature.map.domain.model.LocationModel
import cloud.pensive.feature.map.domain.model.UserLocation
import cloud.pensive.feature.map.domain.util.LocationError
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for location data operations
 * Defines contracts that data layer must implement
 * Uses typed Result<T, LocationError> for type-safe error handling
 */
interface LocationRepository {

    /**
     * Fetches the current user's location from device
     *
     * @return Flow<Result<UserLocation, LocationError>>
     *         - Success: Location fetched successfully
     *         - Error: NoPermission, Timeout, Unknown
     */
    fun getCurrentUserLocation(): Flow<Result<UserLocation, LocationError>>

    /**
     * Retrieves list of predefined searchable locations
     *
     * @return Flow<Result<List<LocationModel>, LocationError>>
     *         - Success: List of available locations
     *         - Error: Rarely fails (hardcoded data)
     */
    fun getPredefinedLocations(): Flow<Result<List<LocationModel>, LocationError>>

    /**
     * Checks if location permission is currently granted
     *
     * @return Result<Boolean, LocationError>
     *         - Success(true): Permission granted
     *         - Success(false): Permission denied
     *         - Error: Permission check failed
     */
    suspend fun hasLocationPermission(): Result<Boolean, LocationError>
    
}