package com.akotsenko.elevateguard.sources.user.entities

import com.google.gson.annotations.SerializedName

data class UpdateUserRequestEntity(
    @SerializedName("first_name") val userFirstName: String? = null,
    @SerializedName("last_name") val userLastName: String? = null,
    @SerializedName("email") val userEmail: String? = null,
    @SerializedName("password") val userPassword: String? = null,
    @SerializedName("position_id") val userPositionId: Int? = null,
    @SerializedName("facility_id") val userFacilityId: Int? = null,
    @SerializedName("mobile") val userMobile: String? = null,
    @SerializedName("role") val userRole: String? = null,
    @SerializedName("is_receive_email_notif") val userIsReceiveEmailNotification: Int? = null,
    @SerializedName("is_receive_sms_notif") val userIsReceiveSmsNotification: Int? = null,
    @SerializedName("is_receive_push_notif") val userIsReceivePushNotification: Int? = null
)
