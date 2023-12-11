package com.akotsenko.elevateguard.sources.auth.entities

import com.akotsenko.elevateguard.model.auth.entities.Account
import com.google.gson.annotations.SerializedName

data class LoginResponseEntity(
    @SerializedName("token") val token: String,
    @SerializedName("user") val userAuthEntity: UserAuthEntity
) {
    fun toAccount(): Account {
        return Account(
            token = token,
            firstName = userAuthEntity.firstName,
            lastName = userAuthEntity.lastName,
            id = userAuthEntity.id,
            positionId = userAuthEntity.positionId,
            email = userAuthEntity.email,
            mobile = userAuthEntity.mobile,
            role = userAuthEntity.role,
            isReceiveEmailNotification = userAuthEntity.isReceiveEmailNotification,
            isReceivePushNotification = userAuthEntity.isReceivePushNotification,
            isReceiveSmsNotification = userAuthEntity.isReceiveSmsNotification
        )
    }
}
