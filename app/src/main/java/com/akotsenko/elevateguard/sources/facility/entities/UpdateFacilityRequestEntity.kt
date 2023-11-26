package com.akotsenko.elevateguard.sources.facility.entities

import com.google.gson.annotations.SerializedName

data class UpdateFacilityRequestEntity(
    @SerializedName("name") val name: String
)
