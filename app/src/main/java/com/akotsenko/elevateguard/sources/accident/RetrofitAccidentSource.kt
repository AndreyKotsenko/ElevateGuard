package com.akotsenko.elevateguard.sources.accident

import com.akotsenko.elevateguard.model.accident.AccidentSource
import com.akotsenko.elevateguard.model.accident.entities.Accident
import com.akotsenko.elevateguard.sources.base.BaseRetrofitSource

class RetrofitAccidentSource: BaseRetrofitSource(), AccidentSource {

    private val accidentApi = retrofit.create(AccidentApi::class.java)

    override suspend fun getAccident(authToken: String, accidentId: Int): Accident = wrapRetrofitExceptions {
        accidentApi.getAccidentById(accidentId = accidentId.toString(), authToken = BEARER_TOKEN + authToken).toAccident()
    }

    override suspend fun getAccidentsByFacility(authToken: String, facilityId: Int): List<Accident> = wrapRetrofitExceptions {
        accidentApi.getAccidentsByFacility(BEARER_TOKEN + authToken, facilityId.toString()).toAccidents()
    }

    override suspend fun createAccident(authToken: String, constructionId: String) {
        wrapRetrofitExceptions {
            accidentApi.createAccident(BEARER_TOKEN + authToken, constructionId)
        }
    }

    companion object {
        private const val BEARER_TOKEN = "Bearer "
    }
}