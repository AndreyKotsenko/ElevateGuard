package com.akotsenko.elevateguard.model.accident

import com.akotsenko.elevateguard.model.accident.entities.Accident
import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.wrapBackendExceptions

class AccidentRepository(
    private val accidentSource: AccidentSource,
    private val appSettings: AppSettings
) {

    suspend fun getAccident(accidentId: Int): Accident = wrapBackendExceptions {
        return accidentSource.getAccident(appSettings.getSettingsUserDataState().token, accidentId)
    }

    suspend fun getAccidentsByFacility(): List<Accident> = wrapBackendExceptions {
        return accidentSource.getAccidentsByFacility(appSettings.getSettingsUserDataState().token, appSettings.getCurrentFacilityId())
    }

    suspend fun createAccident(constructionId: Int) {
        wrapBackendExceptions {
            accidentSource.createAccident(appSettings.getSettingsUserDataState().token, constructionId.toString())
        }
    }
}