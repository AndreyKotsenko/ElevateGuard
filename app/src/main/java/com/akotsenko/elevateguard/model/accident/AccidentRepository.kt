package com.akotsenko.elevateguard.model.accident

import com.akotsenko.elevateguard.model.accident.entities.Accident
import com.akotsenko.elevateguard.model.settings.AppSettings

class AccidentRepository(
    private val accidentSource: AccidentSource,
    private val appSettings: AppSettings
) {

    suspend fun getAccident(accidentId: Int): Accident {
        return accidentSource.getAccident(appSettings.getSettingsUserDataState().token, accidentId)
    }

    suspend fun getAccidentsByFacility(): List<Accident> {
        return accidentSource.getAccidentsByFacility(appSettings.getSettingsUserDataState().token, appSettings.getSettingsUserDataState().facilityId)
    }

    suspend fun createAccident(constructionId: Int) {
        accidentSource.createAccident(appSettings.getSettingsUserDataState().token, constructionId.toString())
    }
}