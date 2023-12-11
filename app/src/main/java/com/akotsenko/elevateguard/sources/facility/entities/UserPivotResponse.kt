package com.akotsenko.elevateguard.sources.facility.entities

import com.google.gson.annotations.SerializedName

data class UserPivotResponse(
    @SerializedName("facility_id") val facilityId: Int,
    @SerializedName("user_id") val userId: Int
)
