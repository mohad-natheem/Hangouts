package cloud.pensive.feature.map.domain.util

import com.plcoding.core.domain.util.Error

/**
 * Domain-level errors specific to map feature
 * These are abstract business errors that don't expose implementation details
 */
sealed interface LocationError : Error {
    /**
     * No location permission granted by user
     */
    data object NoPermission : LocationError

    /**
     * Location service is not available on device
     */
    data object ServiceUnavailable : LocationError

    /**
     * Location data fetch timed out
     */
    data object Timeout : LocationError

    /**
     * Unknown or unexpected error
     */
    data object Unknown : LocationError
}

