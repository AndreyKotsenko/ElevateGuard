package com.akotsenko.elevateguard.sources.user.entities

import com.google.gson.annotations.SerializedName

data class PivotFacilityOfUserResponseEntity(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("facility_id") val facilityId: Int
)
