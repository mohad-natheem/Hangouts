package cloud.pensive.feature.map.data.manager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.plcoding.core.domain.util.Result
import cloud.pensive.feature.map.domain.util.LocationError

class LocationPermissionManager(private val context: Context) {

    fun hasLocationPermission(): Result<Boolean, LocationError> {
        return try {
            val granted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            Result.Success(granted)
        } catch (e: Exception) {
            Result.Error(LocationError.Unknown)
        }
    }

    fun shouldShowPermissionRationale(): Result<Boolean, LocationError> {
        return try {
            val shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            Result.Success(shouldShow)
        } catch (e: Exception) {
            Result.Error(LocationError.Unknown)
        }
    }
}


