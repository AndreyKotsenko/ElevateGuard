package com.akotsenko.elevateguard.sources.facility.entities

import com.google.gson.annotations.SerializedName

data class CreateFacilitiesResponseEntity(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int
)
