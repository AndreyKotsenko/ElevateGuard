package com.akotsenko.elevateguard.model.settings.entities

import com.akotsenko.elevateguard.model.user.entities.User

data class SettingsUserData(
    val token: String,
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val positionId: Int? = null,
    val email: String,
    val mobile: String,
    val role: String,
    val isReceiveEmailNotification: Int,
    val isReceiveSmsNotification: Int,
    val isReceivePushNotification: Int
) {
    fun toUser(): User {
        return User(
            id = userId,
            firstName = firstName,
            lastName = lastName,
            positionId = positionId,
            email = email,
            mobile = mobile,
            role = role,
            isReceiveEmailNotification = isReceiveEmailNotification,
            isReceiveSmsNotification = isReceiveSmsNotification,
            isReceivePushNotification = isReceivePushNotification
        )
    }
}
