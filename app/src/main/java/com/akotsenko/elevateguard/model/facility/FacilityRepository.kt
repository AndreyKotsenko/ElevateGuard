package com.akotsenko.elevateguard.model.facility

import com.akotsenko.elevateguard.model.EmptyFieldException
import com.akotsenko.elevateguard.model.Field
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.facility.entities.Facility
import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.model.wrapBackendExceptions

class FacilityRepository(private val facilitySource: FacilitySource, private val appSettings: AppSettings) {


    suspend fun getFacility(facilityId: String): Facility = wrapBackendExceptions {
        return facilitySource.getFacility(appSettings.getSettingsUserDataState().token, facilityId)
    }

    suspend fun createFacility(name: String): Int = wrapBackendExceptions {
        if (name.isBlank()) throw EmptyFieldException(Field.FacilityName)

        return facilitySource.createFacility(appSettings.getSettingsUserDataState().token, name = name)
    }

    suspend fun updateFacility(facilityId: String, facility: Facility): String = wrapBackendExceptions {
        if (facility.name.isBlank()) throw EmptyFieldException(Field.FacilityName)

        return facilitySource.updateFacility(appSettings.getSettingsUserDataState().token, facilityId, facility)
    }

    suspend fun getUsersByFacility(): List<User> = wrapBackendExceptions {
        return facilitySource.getUsersByFacility(appSettings.getSettingsUserDataState().token, appSettings.getSettingsUserDataState().facilityId.toString())
    }

    suspend fun getConstructionOfFacility(): List<Construction> = wrapBackendExceptions {
        return facilitySource.getFacility(appSettings.getSettingsUserDataState().token, appSettings.getSettingsUserDataState().facilityId.toString()).constructionOfFacility
    }

    fun getCurrentFacilityId(): String = wrapBackendExceptions {
        return appSettings.getSettingsUserDataState().facilityId.toString()
    }

}