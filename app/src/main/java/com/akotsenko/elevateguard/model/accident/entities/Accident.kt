package com.akotsenko.elevateguard.model.accident.entities

data class Accident(
    val id: Int?,
    val date: String?,
    val facilityId: Int?,
    val facilityName: String?,
    val constructionId: Int?,
    val constructionName: String?
)