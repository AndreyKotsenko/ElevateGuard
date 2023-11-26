package com.akotsenko.elevateguard.model.settings.entities

data class SettingsUserData(
    val token: String,
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val positionId: Int,
    val facilityId: Int,
    val email: String,
    val mobile: String,
    val role: String,
    val isReceiveEmailNotification: Int,
    val isReceiveSmsNotification: Int,
    val isReceivePushNotification: Int
)
