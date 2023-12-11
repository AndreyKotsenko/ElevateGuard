package com.akotsenko.elevateguard.sources.user.entities

import com.google.gson.annotations.SerializedName

data class UpdateUserResponseEntity(
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: UserResponseEntity
)
