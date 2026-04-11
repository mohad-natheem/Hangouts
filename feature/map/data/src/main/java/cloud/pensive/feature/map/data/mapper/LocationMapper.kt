package cloud.pensive.feature.map.data.mapper

import cloud.pensive.feature.map.domain.model.LocationModel
import cloud.pensive.feature.map.domain.model.UserLocation


object LocationMapper {

    fun filterLocations(locations: List<LocationModel>): List<LocationModel> {
        return locations.filter { it.latitude != null && it.longitude != null }
    }
}



