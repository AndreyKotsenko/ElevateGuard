package com.akotsenko.elevateguard.sources.construction.entitties

import com.google.gson.annotations.SerializedName

data class UpdateConstructionRequestEntity(
    @SerializedName("name") val name: String?
)
