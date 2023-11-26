package com.akotsenko.elevateguard.sources.user

import com.akotsenko.elevateguard.sources.user.entities.GetUserResponseEntity
import com.akotsenko.elevateguard.sources.user.entities.UpdateUserRequestEntity
import com.akotsenko.elevateguard.sources.user.entities.UpdateUserResponseEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("users/{userId}")
    suspend fun getUser(@Header("Authorization") authToken: String, @Path("userId") userId: String): GetUserResponseEntity

    @PUT("users/{userId}")
    suspend fun updateUser(@Header("Authorization") authToken: String, @Path("userId") userId: String, @Body user: UpdateUserRequestEntity): UpdateUserResponseEntity

    @DELETE("users/{userId}")
    suspend fun deleteUser(@Header("Authorization") authToken: String, @Path("userId") userId: String)
}