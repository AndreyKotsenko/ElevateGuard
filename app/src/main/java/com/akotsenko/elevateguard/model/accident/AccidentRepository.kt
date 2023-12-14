package com.akotsenko.elevateguard.model.accident

import com.akotsenko.elevateguard.model.*
import com.akotsenko.elevateguard.model.accident.entities.Accident
import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.wrapBackendExceptions

class AccidentRepository(
    private val accidentSource: AccidentSource,
    private val appSettings: AppSettings
) {

    suspend fun getAccident(accidentId: Int): Accident = wrapBackendExceptions {

        val accident = try {
            accidentSource.getAccident(appSettings.getSettingsUserDataState().token, accidentId)
        } catch (e: BackendException) {
            if (e.code == 400) throw AccidentNotFoundException(e.message.toString())
            else e
        }
        return accident as Accident
    }

    suspend fun getAccidentsByFacility(): List<Accident> = wrapBackendExceptions {
        val accidents = try {
            accidentSource.getAccidentsByFacility(appSettings.getSettingsUserDataState().token, appSettings.getCurrentFacilityId())
        } catch (e: BackendException) {
            if (e.code == 400) throw FacilityNotFoundException(e.message.toString())
            else e
        }

        return accidents as List<Accident>
    }

    suspend fun createAccident(constructionId: Int) {
        wrapBackendExceptions {
            try {
                accidentSource.createAccident(appSettings.getSettingsUserDataState().token, constructionId.toString())
            } catch (e: BackendException) {
                if (e.code == 400) throw ConstructionNotFoundException(e.message.toString())
            }
        }
    }
}