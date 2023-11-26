package com.akotsenko.elevateguard.sources.facility.entities

import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.google.gson.annotations.SerializedName

data class ConstructionOfFacilityEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("facility_id") val facilityId: Int
) {
    fun toConstruction(): Construction {
        return Construction(
            id = id,
            name = name,
            facilityId = facilityId
        )
    }
}
