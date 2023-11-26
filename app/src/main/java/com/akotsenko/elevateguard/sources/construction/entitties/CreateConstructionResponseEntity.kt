package com.akotsenko.elevateguard.sources.construction.entitties

import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.google.gson.annotations.SerializedName

data class CreateConstructionResponseEntity(
    @SerializedName("name") val name: String,
    @SerializedName("facility_id") val facilityId: Int,
    @SerializedName("id") val id: Int
) {
    fun toConstruction(): Construction {
        return Construction(
            id = id,
            facilityId = facilityId,
            name = name
        )
    }
}
