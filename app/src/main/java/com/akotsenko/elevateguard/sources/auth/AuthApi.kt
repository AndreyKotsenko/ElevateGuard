package com.akotsenko.elevateguard.sources.auth

import com.akotsenko.elevateguard.sources.auth.entities.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(@Body loginData: LoginRequestEntity): LoginResponseEntity

    @POST("logout")
    suspend fun logout(@Header("Authorization") authToken: String): LogoutResponseEntity

    @POST("register")
    suspend fun register(
        @Header("Authorization") authToken: String,
        @Body registerData: RegisterRequestEntity
    ): RegisterResponseEntity
}