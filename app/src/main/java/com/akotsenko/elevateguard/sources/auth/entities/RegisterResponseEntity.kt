package com.akotsenko.elevateguard.sources.auth.entities

import com.akotsenko.elevateguard.model.auth.entities.Account
import com.google.gson.annotations.SerializedName

data class RegisterResponseEntity(
    @SerializedName("first_name") val userFirstName: String,
    @SerializedName("last_name") val userLastName: String,
    @SerializedName("email") val userEmail: String,
    @SerializedName("mobile") val userMobile: String,
    @SerializedName("role") val userRole: String,
    @SerializedName("is_receive_email_notif") val userIsReceiveEmailNotification: Int,
    @SerializedName("is_receive_sms_notif") val userIsReceiveSmsNotification: Int,
    @SerializedName("is_receive_push_notif") val userIsReceivePushNotification: Int,
    @SerializedName("position_id") val userPositionId: Int?,
    @SerializedName("facility_id") val userFacilityId: Int,
    @SerializedName("id") val userId: Int
) {
    fun toAccount(): Account {
        return Account(
            firstName = userFirstName,
            lastName = userLastName,
            id = userId,
            positionId = userPositionId!!,
            facilityId = userFacilityId,
            email = userEmail,
            mobile = userMobile,
            role = userRole,
            isReceiveEmailNotification = userIsReceiveEmailNotification,
            isReceivePushNotification = userIsReceivePushNotification,
            isReceiveSmsNotification = userIsReceiveSmsNotification,
            token = ""
        )
    }
}
