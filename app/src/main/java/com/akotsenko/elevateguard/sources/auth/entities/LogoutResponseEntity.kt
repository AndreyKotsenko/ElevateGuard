package com.akotsenko.elevateguard.sources.auth.entities

import com.google.gson.annotations.SerializedName

data class LogoutResponseEntity(
    @SerializedName("message") val message: String
)
