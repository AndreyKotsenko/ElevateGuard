package com.akotsenko.elevateguard.model.construction

import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.settings.AppSettings

class ConstructionRepository(private val constructionSource: ConstructionSource, private val appSettings: AppSettings) {

    suspend fun createConstruction(construction: Construction): Construction {
        construction.facilityId = appSettings.getSettingsUserDataState().facilityId
        return constructionSource.createConstruction(appSettings.getSettingsUserDataState().token, construction)
    }

    suspend fun updateConstruction(
        constructionId: String,
        construction: Construction
    ): String {
        return constructionSource.updateConstruction(appSettings.getSettingsUserDataState().token, constructionId, construction)
    }

    suspend fun deleteConstruction(constructionId: String) {
        constructionSource.deleteConstruction(appSettings.getSettingsUserDataState().token, constructionId)
    }
}