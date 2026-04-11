package cloud.pensive.feature.map.data.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import com.plcoding.core.domain.util.Result
import cloud.pensive.feature.map.data.manager.LocationPermissionManager
import cloud.pensive.feature.map.data.source.LocalLocationDataSource
import cloud.pensive.feature.map.data.source.RemoteLocationDataSource
import cloud.pensive.feature.map.domain.model.LocationModel
import cloud.pensive.feature.map.domain.model.UserLocation
import cloud.pensive.feature.map.domain.util.LocationError
import cloud.pensive.feature.map.domain.repository.LocationRepository

/**
 * Implementation of LocationRepository interface
 * Coordinates between multiple data sources and managers
 *
 * Responsibilities:
 * - Fetch current user location from remote source (device GPS)
 * - Fetch predefined locations from local source
 * - Check location permission status
 * - Determine if permission rationale should be shown
 */
class LocationRepositoryImpl(
    private val remoteDataSource: RemoteLocationDataSource,
    private val localDataSource: LocalLocationDataSource,
    private val permissionManager: LocationPermissionManager
) : LocationRepository {

    override fun getCurrentUserLocation(): Flow<Result<UserLocation, LocationError>> {
        return remoteDataSource.getCurrentUserLocation()
    }

    override fun getPredefinedLocations(): Flow<Result<List<LocationModel>, LocationError>> {
        return localDataSource.getPredefinedLocations()
    }

    override suspend fun hasLocationPermission(): Result<Boolean, LocationError> {
        return permissionManager.hasLocationPermission()
    }

}


