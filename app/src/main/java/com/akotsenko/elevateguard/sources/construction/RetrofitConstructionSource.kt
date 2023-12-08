package com.akotsenko.elevateguard.sources.construction

import com.akotsenko.elevateguard.model.construction.ConstructionSource
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.sources.auth.RetrofitAuthSource
import com.akotsenko.elevateguard.sources.base.BaseRetrofitSource
import com.akotsenko.elevateguard.sources.construction.entitties.CreateConstructionRequestEntity
import com.akotsenko.elevateguard.sources.construction.entitties.UpdateConstructionRequestEntity

class RetrofitConstructionSource: BaseRetrofitSource(), ConstructionSource {

    private val constructionApi = retrofit.create(ConstructionApi::class.java)

    override suspend fun createConstruction(
        authToken: String,
        construction: Construction
    ): Construction = wrapRetrofitExceptions {
        val createConstructionRequestEntity = CreateConstructionRequestEntity(
            facilityId = construction.facilityId,
            name = construction.name
        )

        constructionApi.createConstruction(BEARER_TOKEN + authToken, createConstructionRequestEntity).toConstruction()
    }

    override suspend fun updateConstruction(
        authToken: String,
        constructionId: String,
        construction: Construction
    ): String = wrapRetrofitExceptions {
        val updateConstructionRequestEntity = UpdateConstructionRequestEntity(
            name = construction.name
        )

        constructionApi.updateConstruction(BEARER_TOKEN + authToken, constructionId, updateConstructionRequestEntity).message
    }

    override suspend fun deleteConstruction(authToken: String, constructionId: String) {
        wrapRetrofitExceptions {
            constructionApi.deleteConstruction(BEARER_TOKEN + authToken, constructionId)
        }
    }

    companion object {
        private const val BEARER_TOKEN = "Bearer "
    }
}