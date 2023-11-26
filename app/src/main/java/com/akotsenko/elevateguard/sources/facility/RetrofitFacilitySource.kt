package com.akotsenko.elevateguard.sources.facility

import com.akotsenko.elevateguard.model.facility.FacilitySource
import com.akotsenko.elevateguard.model.facility.entities.Facility
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.sources.base.BaseRetrofitSource
import com.akotsenko.elevateguard.sources.facility.entities.CreateFacilitiesRequestEntity
import com.akotsenko.elevateguard.sources.facility.entities.UpdateFacilityRequestEntity

class RetrofitFacilitySource : BaseRetrofitSource(), FacilitySource {

    private val facilityApi = retrofit.create(FacilityApi::class.java)

    override suspend fun getFacility(authToken: String, facilityId: String): Facility {
        return facilityApi.getFacility(BEARER_TOKEN + authToken, facilityId).toFacility()
    }

    override suspend fun createFacility(authToken: String, name: String): Int {
        val createFacilityRequestEntity = CreateFacilitiesRequestEntity(
            name = name
        )
        return facilityApi.createFacility(BEARER_TOKEN + authToken, createFacilityRequestEntity).id
    }

    override suspend fun updateFacility(
        authToken: String,
        facilityId: String,
        facility: Facility
    ): String {
        val updateFacilityRequestEntity = UpdateFacilityRequestEntity(
            name = facility.name
        )
        return facilityApi.updateFacility(BEARER_TOKEN + authToken, facilityId, updateFacilityRequestEntity)
    }

    override suspend fun getUsersByFacility(authToken: String, facilityId: String): List<User> {
        try {
            println("DATA: " + authToken + " and facilityId: " + facilityId)
            return facilityApi.getUsersByFacility(BEARER_TOKEN + authToken, facilityId).toUsers()
        } catch (e: Exception){
            println("ERROR = " + e.message)
            return emptyList()
        }
    }

    companion object {
        private const val BEARER_TOKEN = "Bearer "
    }

}