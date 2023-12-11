package com.akotsenko.elevateguard.sources.user.entities

import com.akotsenko.elevateguard.model.user.entities.FacilityOfUser
import com.google.gson.annotations.SerializedName

data class GetFacilitiesOfUserResponseEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("pivot") val pivot: PivotFacilityOfUserResponseEntity
) {
    fun toFacilityOfUser(): FacilityOfUser {
        return FacilityOfUser(
            id = id,
            name = name
        )
    }
}
