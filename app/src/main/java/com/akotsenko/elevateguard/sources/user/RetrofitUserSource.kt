package com.akotsenko.elevateguard.sources.user

import com.akotsenko.elevateguard.model.user.UserSource
import com.akotsenko.elevateguard.model.user.entities.FacilityOfUser
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.sources.base.BaseRetrofitSource
import com.akotsenko.elevateguard.sources.user.entities.UpdateUserRequestEntity

class RetrofitUserSource: BaseRetrofitSource(), UserSource {

    private val userApi = retrofit.create(UserApi::class.java)

    override suspend fun getUser(authToken: String, userId: String): User = wrapRetrofitExceptions {
        userApi.getUser(BEARER_TOKEN + authToken, userId).toUser()
    }

    override suspend fun updateUser(authToken: String, userId: String, user: User, password: String?) {

        wrapRetrofitExceptions {
            val updateUserRequestEntity = UpdateUserRequestEntity(
                userFirstName = user.firstName,
                userLastName = user.lastName,
                userEmail = user.email,
                userPassword = password,
                userMobile = user.mobile,
                userRole = user.role,
                userIsReceiveEmailNotification = user.isReceiveEmailNotification,
                userIsReceiveSmsNotification = user.isReceiveSmsNotification,
                userIsReceivePushNotification = user.isReceiveSmsNotification

            )

            userApi.updateUser(BEARER_TOKEN + authToken, userId, updateUserRequestEntity)
        }
    }

    override suspend fun deleteUser(authToken: String, userId: String) {
        wrapRetrofitExceptions {
            userApi.deleteUser(BEARER_TOKEN + authToken, userId)
        }

    }

    override suspend fun getFacilitiesOfUser(authToken: String): List<FacilityOfUser> = wrapRetrofitExceptions {
        userApi.getFacilitiesOfUser(BEARER_TOKEN + authToken).map { it.toFacilityOfUser() }
    }

    companion object {
        private const val BEARER_TOKEN = "Bearer "
    }

}


