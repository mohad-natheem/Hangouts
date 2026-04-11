package cloud.pensive.feature.map.data.source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.plcoding.core.domain.util.Result
import cloud.pensive.feature.map.domain.model.LocationModel
import cloud.pensive.feature.map.domain.model.UserLocation
import cloud.pensive.feature.map.domain.util.LocationError

class LocalLocationDataSource : LocationDataSource {

    override fun getPredefinedLocations(): Flow<Result<List<LocationModel>, LocationError>> = flow {
        emit(
            Result.Success(
                listOf(
                    LocationModel(
                        id = "ny_001",
                        name = "New York",
                        latitude = 40.7128,
                        longitude = -74.0060
                    ),
                    LocationModel(
                        id = "london_001",
                        name = "London",
                        latitude = 51.5074,
                        longitude = -0.1278
                    ),
                    LocationModel(
                        id = "tokyo_001",
                        name = "Tokyo",
                        latitude = 35.6762,
                        longitude = 139.6503
                    ),
                    LocationModel(
                        id = "paris_001",
                        name = "Paris",
                        latitude = 48.8566,
                        longitude = 2.3522
                    ),
                    LocationModel(
                        id = "sydney_001",
                        name = "Sydney",
                        latitude = -33.8688,
                        longitude = 151.2093
                    )
                )
            )
        )
    }

    override fun getCurrentUserLocation(): Flow<Result<UserLocation, LocationError>> = flow {
        emit(Result.Error(LocationError.ServiceUnavailable))
    }
}

