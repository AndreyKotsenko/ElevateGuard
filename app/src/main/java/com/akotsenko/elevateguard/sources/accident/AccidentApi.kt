package com.akotsenko.elevateguard.sources.accident

import com.akotsenko.elevateguard.sources.accident.entities.GetAccidentByIdResponseEntity
import com.akotsenko.elevateguard.sources.accident.entities.GetAccidentsByFacilityResponseEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AccidentApi {

    @GET("constructions/{constructionId}/accidents/create")
    suspend fun createAccident(@Header("Authorization") authToken: String, @Path("constructionId") constructionId: String)

    @GET("facilities/{facilityId}/accidents")
    suspend fun getAccidentsByFacility(@Header("Authorization") authToken: String, @Path("facilityId") facilityId: String): GetAccidentsByFacilityResponseEntity

    @GET("accidents/{accidentId}")
    suspend fun getAccidentById(@Header("Authorization") authToken: String, @Path("accidentId") accidentId: String): GetAccidentByIdResponseEntity
}