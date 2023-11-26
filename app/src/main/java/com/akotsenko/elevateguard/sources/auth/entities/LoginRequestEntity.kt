package com.akotsenko.elevateguard.sources.auth.entities

import com.google.gson.annotations.SerializedName

data class LoginRequestEntity(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)