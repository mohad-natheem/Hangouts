package cloud.pensive.feature.map.domain.model

data class UserLocation(
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis(),
    val accuracy: Float? = null
)
