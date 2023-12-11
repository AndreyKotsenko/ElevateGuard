package com.akotsenko.elevateguard.model.settings

import com.akotsenko.elevateguard.model.settings.entities.SettingsUserData

class AppSettingsRepository(private val appSettings: AppSettings) {

    fun saveIsSignedInState(isSignedIn: Boolean) {
        appSettings.saveIsSignedInState(isSignedIn)
    }

    fun getIsSignedInState(): Boolean = appSettings.getIsSignedInState()

    fun saveCurrentFacilityId(facilityId: Int) {
        appSettings.saveCurrentFacilityId(facilityId)
    }

    fun getCurrentFacilityId(): Int {
        return appSettings.getCurrentFacilityId()
    }

    fun saveSettingsUserDataState(settingsUserData: SettingsUserData) {
        appSettings.saveSettingsUserDataState(settingsUserData)
    }

    fun getSettingsUserDataState(): SettingsUserData = appSettings.getSettingsUserDataState()

    fun saveFacilityName(name: String) {
        appSettings.saveFacilityName(name)
    }

    fun getFacilityName(): String? = appSettings.getFacilityName()
}