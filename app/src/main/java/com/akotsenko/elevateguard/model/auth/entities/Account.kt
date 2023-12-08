package com.akotsenko.elevateguard.model.auth.entities

import com.akotsenko.elevateguard.model.settings.entities.SettingsUserData

data class Account (
    val token: String,
    val id: Int,
    val firstName: String,
    val lastName: String,
    val positionId: Int? = null,
    val facilityId: Int,
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
            facilityId = facilityId,
            email = email,
            mobile = mobile,
            role = role,
            isReceiveEmailNotification = isReceiveEmailNotification,
            isReceiveSmsNotification = isReceiveSmsNotification,
            isReceivePushNotification = isReceivePushNotification
        )
    }
}
