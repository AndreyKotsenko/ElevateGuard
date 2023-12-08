package com.akotsenko.elevateguard.sources.auth

import com.akotsenko.elevateguard.model.auth.AuthSource
import com.akotsenko.elevateguard.model.auth.entities.Account
import com.akotsenko.elevateguard.model.auth.entities.Credential
import com.akotsenko.elevateguard.model.auth.entities.RegisterData
import com.akotsenko.elevateguard.sources.auth.entities.LoginRequestEntity
import com.akotsenko.elevateguard.sources.auth.entities.RegisterRequestEntity
import com.akotsenko.elevateguard.sources.base.BaseRetrofitSource
import retrofit2.Call

class RetrofitAuthSource: BaseRetrofitSource(), AuthSource {

    private val authApi = retrofit.create(AuthApi::class.java)

    override suspend fun login(loginData: Credential): Account = wrapRetrofitExceptions {
        val loginRequestData = LoginRequestEntity(
            email = loginData.email,
            password = loginData.password
        )

        println("GEY = " + loginRequestData.email)
        val result = authApi.login(loginRequestData).toAccount()
        println("MUST CHLEN = " + result.toString())
        result
    }

    override suspend fun logout(authToken: String): String = wrapRetrofitExceptions {
        authApi.logout(BEARER_TOKEN + authToken).message
    }

    override suspend fun register(
        authToken: String,
        registerData: RegisterData
    ): Account = wrapRetrofitExceptions {
        val registerRequestEntity = RegisterRequestEntity(
            userFirstName = registerData.userFirstName,
            userLastName = registerData.userLastName,
            userEmail = registerData.userEmail,
            userPassword = registerData.userPassword,
            userMobile = registerData.userMobile,
            userRole = registerData.userRole,
            userPositionId = registerData.userPositionId,
            userFacilityId = registerData.userFacilityId,
            userIsReceiveSmsNotification = registerData.userIsReceiveSmsNotification,
            userIsReceivePushNotification = registerData.userIsReceivePushNotification,
            userIsReceiveEmailNotification = registerData.userIsReceiveEmailNotification
        )

        authApi.register(BEARER_TOKEN + authToken, registerRequestEntity).toAccount()
    }

    companion object {
        private const val BEARER_TOKEN = "Bearer "
    }


}