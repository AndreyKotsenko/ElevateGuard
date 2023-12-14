package com.akotsenko.elevateguard.model.facility

import com.akotsenko.elevateguard.model.*
import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.facility.entities.Facility
import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.model.wrapBackendExceptions

class FacilityRepository(private val facilitySource: FacilitySource, private val appSettings: AppSettings) {


    suspend fun getFacility(facilityId: String): Facility = wrapBackendExceptions {

        val facility = try {
            facilitySource.getFacility(appSettings.getSettingsUserDataState().token, facilityId)
        } catch (e: BackendException) {
            if (e.code == 400) throw FacilityNotFoundException(e.message.toString())
            else e
        }
        return facility as Facility
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

        val result = try {
            facilitySource.updateFacility(appSettings.getSettingsUserDataState().token, facilityId, facilityName)
        } catch (e: BackendException) {
            if (e.code == 400) throw FacilityNotFoundException(e.message.toString())
            else e
        }

        return result as String
    }

    suspend fun getUsersByFacility(): List<User> = wrapBackendExceptions {

        val users = try {
            facilitySource.getUsersByFacility(appSettings.getSettingsUserDataState().token, appSettings.getCurrentFacilityId().toString())
        } catch (e: BackendException) {
            if(e.code == 400) throw FacilityNotFoundException(e.message.toString())
            else e
        }
        return users as List<User>
    }

    suspend fun getConstructionOfFacility(): List<Construction> = wrapBackendExceptions {

        val constructions = try {
            facilitySource.getFacility(appSettings.getSettingsUserDataState().token, appSettings.getCurrentFacilityId().toString()).constructionOfFacility
        } catch (e: BackendException) {
            if (e.code == 400) throw FacilityNotFoundException(e.message.toString())
            else e
        }
        return constructions as List<Construction>
    }

    fun getCurrentFacilityId(): String = wrapBackendExceptions {
        return appSettings.getCurrentFacilityId().toString()
    }

}