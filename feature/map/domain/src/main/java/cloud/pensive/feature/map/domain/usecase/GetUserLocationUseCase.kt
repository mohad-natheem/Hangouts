package cloud.pensive.feature.map.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.plcoding.core.domain.util.Result
import cloud.pensive.feature.map.domain.model.UserLocation
import cloud.pensive.feature.map.domain.util.LocationError
import cloud.pensive.feature.map.domain.repository.LocationRepository

/**
 * Use case for fetching user's current location
 *
 * Business Rules:
 * 1. Check if permission is granted first
 * 2. Return NoPermission error if not granted
 * 3. Fetch and emit location if permission granted
 */
class GetUserLocationUseCase(
    private val locationRepository: LocationRepository
) {

    operator fun invoke(): Flow<Result<UserLocation, LocationError>> = flow {
        // Step 1: Check permission
        val permissionResult = locationRepository.hasLocationPermission()

        when (permissionResult) {
            // Permission check failed - propagate error
            is Result.Error -> {
                emit(permissionResult)
                return@flow
            }

            // Permission check succeeded
            is Result.Success -> {
                if (!permissionResult.data) {
                    // No permission - emit error
                    emit(Result.Error(LocationError.NoPermission))
                    return@flow
                }
            }
        }

        // Step 2: Permission granted - fetch location and emit all results
        locationRepository.getCurrentUserLocation().collect { result ->
            emit(result)
        }
    }
}

