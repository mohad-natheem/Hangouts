package cloud.pensive.feature.map.data.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.plcoding.core.domain.util.Result
import cloud.pensive.feature.map.domain.model.LocationModel
import cloud.pensive.feature.map.domain.model.UserLocation
import cloud.pensive.feature.map.domain.util.LocationError
import cloud.pensive.feature.map.data.manager.LocationFetcher


class RemoteLocationDataSource(
    private val locationFetcher: LocationFetcher
) : LocationDataSource {

    override fun getCurrentUserLocation(): Flow<Result<UserLocation, LocationError>> = flow {
        try {
            val location = locationFetcher.getLastKnownLocation()

            if (location != null) {
                // Success - emit location
                emit(Result.Success(location))
            } else {
                // No location available
                emit(Result.Error(LocationError.ServiceUnavailable))
            }
        } catch (e: Exception) {
            // Unexpected error
            emit(Result.Error(LocationError.Unknown))
        }
    }

    override fun getPredefinedLocations(): Flow<Result<List<LocationModel>, LocationError>> = flow {
        emit(Result.Error(LocationError.ServiceUnavailable))
    }
}

