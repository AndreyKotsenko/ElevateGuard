package com.akotsenko.elevateguard.model.auth.entities

import com.akotsenko.elevateguard.model.settings.entities.SettingsUserData

data class Account (
    val token: String,
    val id: Int,
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
    fun toSettingsUserData(): SettingsUserData {
        return SettingsUserData(
            token = token,
            userId = id,
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
