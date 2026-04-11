package cloud.pensive.feature.map.presentation.util

import android.Manifest
import android.app.Activity
import androidx.core.app.ActivityCompat
import cloud.pensive.feature.map.domain.util.LocationError
import com.plcoding.core.domain.util.Result

/**
 * General-purpose utility for checking Android permission rationale
 *
 * This lives in the PRESENTATION layer because:
 * ✓ Permission rationale checking is a UI concern, not business logic
 * ✓ Requires Activity context which is naturally available in presentation
 * ✓ Used for UI decision making (which dialog to show)
 * ✓ Can be reused across different screens/features
 *
 * This keeps the domain layer clean and framework-independent.
 */
class PermissionRationaleChecker() {

    /**
     * Check if we should show the permission rationale dialog
     *
     * This directly uses Android APIs (which is allowed in presentation layer)
     *
     * @param activity - The Activity context (get from LocalContext.current as? Activity)
     * @param permission - The Android permission to check (e.g., Manifest.permission.ACCESS_FINE_LOCATION)
     * @return true if rationale should be shown, false if user selected "don't ask again"
     */
    fun shouldShowPermissionRationale(
        activity: Activity,
        permission: String
    ): Boolean {
        return try {
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
        } catch (e: Exception) {
            // If check fails for any reason, default to false (don't show rationale)
            false
        }
    }

    /**
     * Convenience function for location permission specifically
     *
     * @param activity - The Activity context
     * @return true if location permission rationale should be shown
     */
    fun shouldShowLocationPermissionRationale(activity: Activity): Result<Boolean, LocationError> {
        try {
            val shouldShowRationale =
                shouldShowPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            return Result.Success(shouldShowRationale)
        } catch (e: Exception) {
            return Result.Error(LocationError.Unknown)
        }
    }
}

