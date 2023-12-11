package com.akotsenko.elevateguard.screens

import androidx.lifecycle.ViewModel
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.settings.AppSettingsRepository
import com.akotsenko.elevateguard.model.settings.entities.SettingsUserData

class MainActivityViewModel(private val appSettingsRepository: AppSettingsRepository = Singletons.appSettingsRepository) :
    ViewModel() {

    fun saveIsSignedInState(isSignedIn: Boolean) {
        appSettingsRepository.saveIsSignedInState(isSignedIn)
    }

    fun getIsSignedInState(): Boolean {
        return appSettingsRepository.getIsSignedInState()
    }

    fun saveSettingsUserDataState(settingsUserData: SettingsUserData) {
        appSettingsRepository.saveSettingsUserDataState(settingsUserData)
    }

    fun getSettingsUserDataState(): SettingsUserData {
        return appSettingsRepository.getSettingsUserDataState()
    }

    fun getFacilityName(): String? = appSettingsRepository.getFacilityName()
}