package com.akotsenko.elevateguard.sources.accident.entities

import com.akotsenko.elevateguard.model.accident.entities.Accident
import com.google.gson.annotations.SerializedName

data class AccidentOfFacilityEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("construction_id") val constructionId: Int,
    @SerializedName("construction_name") val constructionName: String,
    @SerializedName("date") val date: String
) {
    fun toAccident(facilityId: Int, facilityName: String): Accident {
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
