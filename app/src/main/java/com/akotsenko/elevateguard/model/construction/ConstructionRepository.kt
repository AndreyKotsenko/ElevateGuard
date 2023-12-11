package com.akotsenko.elevateguard.model.construction

import com.akotsenko.elevateguard.model.EmptyFieldException
import com.akotsenko.elevateguard.model.Field
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.wrapBackendExceptions

class ConstructionRepository(private val constructionSource: ConstructionSource, private val appSettings: AppSettings) {

    suspend fun createConstruction(construction: Construction): Construction = wrapBackendExceptions {
        if (construction.name?.isBlank() == true) throw EmptyFieldException(Field.ConstructionName)

        construction.facilityId = appSettings.getCurrentFacilityId()
        return constructionSource.createConstruction(appSettings.getSettingsUserDataState().token, construction)
    }

    suspend fun updateConstruction(
        constructionId: String,
        construction: Construction
    ): String = wrapBackendExceptions {
        if (construction.name?.isBlank() == true) throw EmptyFieldException(Field.ConstructionName)

        return constructionSource.updateConstruction(appSettings.getSettingsUserDataState().token, constructionId, construction)
    }

    suspend fun deleteConstruction(constructionId: String) {
        wrapBackendExceptions {
            constructionSource.deleteConstruction(appSettings.getSettingsUserDataState().token, constructionId)
        }
    }
}