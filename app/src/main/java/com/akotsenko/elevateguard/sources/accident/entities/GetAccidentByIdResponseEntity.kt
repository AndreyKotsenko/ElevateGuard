package com.akotsenko.elevateguard.sources.accident.entities

import com.akotsenko.elevateguard.model.accident.entities.Accident
import com.akotsenko.elevateguard.sources.user.entities.GetUserResponseEntity
import com.google.gson.annotations.SerializedName

data class GetAccidentByIdResponseEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("facility_id") val facilityId: Int,
    @SerializedName("facility_name") val facilityName: String,
    @SerializedName("construction_id") val constructionId: Int,
    @SerializedName("construction_name") val constructionName: String,
    @SerializedName("notified_users") val notified_users: List<GetUserResponseEntity>
) {
    fun toAccident(): Accident {
        return Accident(
            id = id,
            date = date,
            facilityId = facilityId,
            facilityName = facilityName,
            constructionId = constructionId,
            constructionName = constructionName
        )
    }
}
