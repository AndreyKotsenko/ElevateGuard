package com.akotsenko.elevateguard.model.user.entities

data class User(
    val id: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val positionId: Int? = null,
    val facilityId: Int? = null,
    val email: String? = null,
    val mobile: String? = null,
    val role: String? = null,
    val isReceiveEmailNotification: Int? = null,
    val isReceiveSmsNotification: Int? = null,
    val isReceivePushNotification: Int? = null
)
