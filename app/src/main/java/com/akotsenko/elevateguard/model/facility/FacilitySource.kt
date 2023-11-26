package com.akotsenko.elevateguard.model.facility

import com.akotsenko.elevateguard.model.facility.entities.Facility
import com.akotsenko.elevateguard.model.user.entities.User

interface FacilitySource {

    suspend fun getFacility(authToken: String, facilityId: String): Facility

    suspend fun createFacility(authToken: String, name: String): Int

    suspend fun updateFacility(authToken: String, facilityId: String, facility: Facility): String

    suspend fun getUsersByFacility(authToken: String, facilityId: String): List<User>

}