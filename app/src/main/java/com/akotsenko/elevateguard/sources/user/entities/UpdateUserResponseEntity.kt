package com.akotsenko.elevateguard.sources.user.entities

import com.google.gson.annotations.SerializedName

data class UpdateUserResponseEntity(
    @SerializedName("id") val userId: Long,
    @SerializedName("first_name") val userFirstName: String,
    @SerializedName("last_name") val userLastName: String,
    @SerializedName("position_id") val userPositionId: Int,
    @SerializedName("facility_id") val userFacilityId: Int,
    @SerializedName("email") val userEmail: String,
    @SerializedName("mobile") val userMobile: String,
    @SerializedName("role") val userRole: String,
    @SerializedName("is_receive_email_notif") val userIsReceiveEmailNotification: Int,
    @SerializedName("is_receive_sms_notif") val userIsReceiveSmsNotification: Int,
    @SerializedName("is_receive_push_notif") val userIsReceivePushNotification: Int
)
