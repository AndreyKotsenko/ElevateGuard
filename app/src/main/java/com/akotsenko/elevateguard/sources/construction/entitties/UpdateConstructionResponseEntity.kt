package com.akotsenko.elevateguard.sources.construction.entitties

import com.google.gson.annotations.SerializedName

data class UpdateConstructionResponseEntity(
    @SerializedName("message") val message: String
)