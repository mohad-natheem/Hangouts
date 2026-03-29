package cloud.pensive.hangouts.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class OverpassResponse(
    val elements: List<OverpassElement>
)

@Serializable
data class OverpassElement(
    val id: Long,
    val lat: Double? = null,
    val lon: Double? = null,
    val tags: Map<String, String>?
)