package com.akotsenko.elevateguard.model.auth.entities

data class RegisterData(
    val userFirstName: String,
    val userLastName: String,
    val userEmail: String,
    val userPassword: String,
    val userMobile: String,
    val userRole: String,
    val userFacilityId: Int,
    val userIsReceiveEmailNotification: Int,
    val userIsReceiveSmsNotification: Int,
    val userIsReceivePushNotification: Int
)