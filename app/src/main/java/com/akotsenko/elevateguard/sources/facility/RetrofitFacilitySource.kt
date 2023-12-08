package com.akotsenko.elevateguard.sources.facility

import com.akotsenko.elevateguard.model.facility.FacilitySource
import com.akotsenko.elevateguard.model.facility.entities.Facility
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.sources.base.BaseRetrofitSource
import com.akotsenko.elevateguard.sources.facility.entities.CreateFacilitiesRequestEntity
import com.akotsenko.elevateguard.sources.facility.entities.UpdateFacilityRequestEntity

class RetrofitFacilitySource : BaseRetrofitSource(), FacilitySource {

    private val facilityApi = retrofit.create(FacilityApi::class.java)

    override suspend fun getFacility(authToken: String, facilityId: String): Facility = wrapRetrofitExceptions {
        facilityApi.getFacility(BEARER_TOKEN + authToken, facilityId).toFacility()
    }

    override suspend fun createFacility(authToken: String, name: String): Int = wrapRetrofitExceptions {
        val createFacilityRequestEntity = CreateFacilitiesRequestEntity(
            name = name
        )
        facilityApi.createFacility(BEARER_TOKEN + authToken, createFacilityRequestEntity).id
    }

    override suspend fun updateFacility(
        authToken: String,
        facilityId: String,
        facility: Facility
    ): String = wrapRetrofitExceptions{
        val updateFacilityRequestEntity = UpdateFacilityRequestEntity(
            name = facility.name
        )
        facilityApi.updateFacility(BEARER_TOKEN + authToken, facilityId, updateFacilityRequestEntity)
    }

    override suspend fun getUsersByFacility(authToken: String, facilityId: String): List<User> = wrapRetrofitExceptions {
        facilityApi.getUsersByFacility(BEARER_TOKEN + authToken, facilityId).toUsers()
    }

    companion object {
        private const val BEARER_TOKEN = "Bearer "
    }

}