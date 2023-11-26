package com.akotsenko.elevateguard.model.facility

import com.akotsenko.elevateguard.model.construction.entities.Construction
import com.akotsenko.elevateguard.model.facility.entities.Facility
import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.user.entities.User

class FacilityRepository(private val facilitySource: FacilitySource, private val appSettings: AppSettings) {


    suspend fun getFacility(facilityId: String): Facility {
        return facilitySource.getFacility(appSettings.getSettingsUserDataState().token, facilityId)
    }

    suspend fun createFacility(name: String): Int {
        return facilitySource.createFacility(appSettings.getSettingsUserDataState().token, name = name)
    }

    suspend fun updateFacility(facilityId: String, facility: Facility): String {
        return facilitySource.updateFacility(appSettings.getSettingsUserDataState().token, facilityId, facility)
    }

    suspend fun getUsersByFacility(): List<User> {
        return facilitySource.getUsersByFacility(appSettings.getSettingsUserDataState().token, appSettings.getSettingsUserDataState().facilityId.toString())
    }

    suspend fun getConstructionOfFacility(): List<Construction> {
        return facilitySource.getFacility(appSettings.getSettingsUserDataState().token, appSettings.getSettingsUserDataState().facilityId.toString()).constructionOfFacility
    }

    fun getCurrentFacilityId(): String {
        return appSettings.getSettingsUserDataState().facilityId.toString()
    }

}