package com.akotsenko.elevateguard.sources.facility.entities

import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.sources.user.entities.GetUserResponseEntity
import com.google.gson.annotations.SerializedName

data class GetUsersByFacilityResponseEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    val users: List<GetUserResponseEntity>
) {
    fun toUsers(): List<User> {
        return users.map {
            it.toUser()
        }
    }
}
