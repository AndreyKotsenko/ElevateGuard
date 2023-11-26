package com.akotsenko.elevateguard.model.accident

import com.akotsenko.elevateguard.model.accident.entities.Accident

interface AccidentSource {

    suspend fun getAccident(authToken: String, accidentId: Int): Accident

    suspend fun getAccidentsByFacility(authToken: String, facilityId: Int): List<Accident>

    suspend fun createAccident(authToken: String, constructionId: String)
}