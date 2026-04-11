package cloud.pensive.feature.map.domain.usecase

import com.plcoding.core.domain.util.Result
import cloud.pensive.feature.map.domain.util.LocationError
import cloud.pensive.feature.map.domain.repository.LocationRepository

class CheckLocationPermissionUseCase(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(): Result<Boolean, LocationError> {
        return locationRepository.hasLocationPermission()
    }
}

