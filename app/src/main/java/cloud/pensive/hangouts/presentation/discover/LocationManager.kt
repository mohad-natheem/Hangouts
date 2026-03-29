package cloud.pensive.hangouts.presentation.discover

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationManager(private val context: Context) {

    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)


    fun shouldShowPermissionRationale(context: Context): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            context as Activity, Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentUserLocation(): Location? {
        return suspendCoroutine { cont ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    cont.resume(location)
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        }
    }

    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}