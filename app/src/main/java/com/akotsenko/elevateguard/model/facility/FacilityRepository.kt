package com.akotsenko.elevateguard.model.facility

import com.akotsenko.elevateguard.model.*
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

        val facilityId = try {
            facilitySource.createFacility(appSettings.getSettingsUserDataState().token, name = name)
        } catch (e: BackendException) {
            if (e.code == 400) throw FacilityAlreadyExistException(e.message.toString())
            else e
        }

        return facilityId as Int
    }

    suspend fun updateFacility(facilityId: String, facilityName: String): String = wrapBackendExceptions {
        if (facilityName.isBlank()) throw EmptyFieldException(Field.FacilityName)

        return facilitySource.updateFacility(appSettings.getSettingsUserDataState().token, facilityId, facilityName)
    }

    suspend fun getUsersByFacility(): List<User> = wrapBackendExceptions {
        return facilitySource.getUsersByFacility(appSettings.getSettingsUserDataState().token, appSettings.getCurrentFacilityId().toString())
    }

    suspend fun getConstructionOfFacility(): List<Construction> = wrapBackendExceptions {
        return facilitySource.getFacility(appSettings.getSettingsUserDataState().token, appSettings.getCurrentFacilityId().toString()).constructionOfFacility
    }

    fun getCurrentFacilityId(): String = wrapBackendExceptions {
        return appSettings.getCurrentFacilityId().toString()
    }

}