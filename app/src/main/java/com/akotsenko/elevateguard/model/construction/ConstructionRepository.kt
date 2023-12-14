package com.akotsenko.elevateguard.model.construction

import com.akotsenko.elevateguard.model.*
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.wrapBackendExceptions

class ConstructionRepository(private val constructionSource: ConstructionSource, private val appSettings: AppSettings) {

    suspend fun createConstruction(construction: Construction): Construction = wrapBackendExceptions {
        if (construction.name?.isBlank() == true) throw EmptyFieldException(Field.ConstructionName)

        construction.facilityId = appSettings.getCurrentFacilityId()
        val createdConstruction = try {
            constructionSource.createConstruction(appSettings.getSettingsUserDataState().token, construction)
        } catch (e: BackendException) {
            if (e.code == 400) throw FacilityNotFoundException(e.message.toString())
            else e
        }
        return createdConstruction as Construction
    }

    suspend fun updateConstruction(
        constructionId: String,
        construction: Construction
    ): String = wrapBackendExceptions {
        if (construction.name?.isBlank() == true) throw EmptyFieldException(Field.ConstructionName)

        val result = try {
            constructionSource.updateConstruction(appSettings.getSettingsUserDataState().token, constructionId, construction)
        } catch (e: BackendException) {
            if (e.code == 400) throw FacilityNotFoundException(e.message.toString())
            else e
        }

        return result as String
    }

    suspend fun deleteConstruction(constructionId: String) {
        wrapBackendExceptions {
            try {
                constructionSource.deleteConstruction(appSettings.getSettingsUserDataState().token, constructionId)
            } catch (e: BackendException) {
                if (e.code == 400) throw FacilityNotFoundException(e.message.toString())
            }
        }
    }
}