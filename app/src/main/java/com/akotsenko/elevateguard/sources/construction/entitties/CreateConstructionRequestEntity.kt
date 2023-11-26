package com.akotsenko.elevateguard.sources.construction.entitties

import com.google.gson.annotations.SerializedName

data class CreateConstructionRequestEntity(
    @SerializedName("name") val name: String?,
    @SerializedName("facility_id") val facilityId: Int?
)
