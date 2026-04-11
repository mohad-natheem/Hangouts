package cloud.pensive.feature.map.domain.usecase

import kotlinx.coroutines.flow.Flow
import com.plcoding.core.domain.util.Result
import cloud.pensive.feature.map.domain.model.LocationModel
import cloud.pensive.feature.map.domain.util.LocationError
import cloud.pensive.feature.map.domain.repository.LocationRepository

class GetPredefinedLocationsUseCase(
    private val locationRepository: LocationRepository
) {

    operator fun invoke(): Flow<Result<List<LocationModel>, LocationError>> {
        return locationRepository.getPredefinedLocations()
    }
}

