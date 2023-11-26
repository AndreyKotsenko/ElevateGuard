package com.akotsenko.elevateguard.sources.accident.entities

import com.akotsenko.elevateguard.model.accident.entities.Accident
import com.google.gson.annotations.SerializedName

data class GetAccidentsByFacilityResponseEntity(
    @SerializedName("facility_id") val facilityId: Int,
    @SerializedName("facility_name") val facilityName: String,
    @SerializedName("accidents") val accidents: List<AccidentOfFacilityEntity>
) {
    fun toAccidents(): List<Accident> {
        return accidents.map {
            it.toAccident(facilityId, facilityName)
        }
    }
}
