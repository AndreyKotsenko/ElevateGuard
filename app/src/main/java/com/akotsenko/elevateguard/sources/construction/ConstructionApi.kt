package com.akotsenko.elevateguard.sources.construction

import com.akotsenko.elevateguard.sources.construction.entitties.CreateConstructionRequestEntity
import com.akotsenko.elevateguard.sources.construction.entitties.CreateConstructionResponseEntity
import com.akotsenko.elevateguard.sources.construction.entitties.UpdateConstructionRequestEntity
import com.akotsenko.elevateguard.sources.construction.entitties.UpdateConstructionResponseEntity
import retrofit2.Call
import retrofit2.http.*

interface ConstructionApi {

    @POST("constructions")
    suspend fun createConstruction(@Header("Authorization") authToken: String, @Body body: CreateConstructionRequestEntity): CreateConstructionResponseEntity

    @PUT("constructions/{constructionId}")
    suspend fun updateConstruction(@Header("Authorization") authToken: String, @Path("constructionId") constructionId: String, @Body body: UpdateConstructionRequestEntity): UpdateConstructionResponseEntity

    @DELETE("constructions/{constructionId}")
    suspend fun deleteConstruction(@Header("Authorization") authToken: String, @Path("constructionId") constructionId: String)
}