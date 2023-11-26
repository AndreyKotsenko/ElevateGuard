package com.akotsenko.elevateguard.sources.accident

import com.akotsenko.elevateguard.model.accident.AccidentSource
import com.akotsenko.elevateguard.model.accident.entities.Accident
import com.akotsenko.elevateguard.sources.base.BaseRetrofitSource

class RetrofitAccidentSource: BaseRetrofitSource(), AccidentSource {

    private val accidentApi = retrofit.create(AccidentApi::class.java)

    override suspend fun getAccident(authToken: String, accidentId: Int): Accident {
        return accidentApi.getAccidentById(accidentId = accidentId.toString(), authToken = authToken).toAccident()

    }

    override suspend fun getAccidentsByFacility(authToken: String, facilityId: Int): List<Accident> {
        return accidentApi.getAccidentsByFacility(authToken, facilityId.toString()).toAccidents()
    }

    override suspend fun createAccident(authToken: String, constructionId: String) {
        accidentApi.createAccident(authToken, constructionId)
    }
}