package cloud.pensive.feature.map.domain.model

data class LocationModel(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val zoom: Float = 14F
)
