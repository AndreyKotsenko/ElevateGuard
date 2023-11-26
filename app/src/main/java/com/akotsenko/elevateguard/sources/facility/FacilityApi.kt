package com.akotsenko.elevateguard.sources.facility

import com.akotsenko.elevateguard.sources.facility.entities.*
import retrofit2.http.*

interface FacilityApi {

    @GET("facilities/{facilityId}")
    suspend fun getFacility(@Header("Authorization") authToken: String, @Path("facilityId") facilityId: String): GetFacilityResponseEntity

    @POST("facilities")
    suspend fun createFacility(@Header("Authorization") authToken: String, @Body name: CreateFacilitiesRequestEntity): CreateFacilitiesResponseEntity

    @PUT("facilities/{facilityId}")
    suspend fun updateFacility(@Header("Authorization") authToken: String, @Path("facilityId") facilityId: String, @Body body: UpdateFacilityRequestEntity): String

    @GET("facilities/{facilityId}/users")
    suspend fun getUsersByFacility(@Header("Authorization") authToken: String, @Path("facilityId") facilityId: String): GetUsersByFacilityResponseEntity
}