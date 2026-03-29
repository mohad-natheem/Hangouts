package cloud.pensive.hangouts.domain.model

data class NearbyPlace(
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    val type: String       // e.g. "restaurant", "hospital", "cafe"
)