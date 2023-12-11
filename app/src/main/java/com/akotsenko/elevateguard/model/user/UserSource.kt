package com.akotsenko.elevateguard.model.user

import com.akotsenko.elevateguard.model.facility.entities.Facility
import com.akotsenko.elevateguard.model.user.entities.FacilityOfUser
import com.akotsenko.elevateguard.model.user.entities.User

interface UserSource {

    suspend fun getUser(authToken: String, userId: String): User

    suspend fun updateUser(authToken: String, userId: String, user: User, password: String?)

    suspend fun deleteUser(authToken: String, userId: String)

    suspend fun getFacilitiesOfUser(authToken: String): List<FacilityOfUser>

}