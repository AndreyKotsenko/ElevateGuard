package com.akotsenko.elevateguard.sources.auth.entities

import com.google.gson.annotations.SerializedName

data class UserAuthEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("position_id") val positionId: Int,
    @SerializedName("facility_id") val facilityId: Int,
    @SerializedName("email") val email: String,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("role") val role: String,
    @SerializedName("is_receive_email_notif") val isReceiveEmailNotification: Int,
    @SerializedName("is_receive_sms_notif") val isReceiveSmsNotification: Int,
    @SerializedName("is_receive_push_notif") val isReceivePushNotification: Int
)
