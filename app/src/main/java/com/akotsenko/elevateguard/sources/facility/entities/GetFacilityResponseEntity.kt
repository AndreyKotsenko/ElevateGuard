package com.akotsenko.elevateguard.sources.facility.entities

import com.akotsenko.elevateguard.model.facility.entities.Facility
import com.google.gson.annotations.SerializedName

data class GetFacilityResponseEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("constructions") val constructionOfFacilityEntities: List<ConstructionOfFacilityEntity>
) {
    fun toFacility(): Facility {
        return Facility(
            name = name,
            constructionOfFacility = constructionOfFacilityEntities.map {
                it.toConstruction()
            }
        )
    }
}
