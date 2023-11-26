package com.akotsenko.elevateguard.model.settings

import com.akotsenko.elevateguard.model.settings.entities.SettingsUserData


interface AppSettings {

    fun saveIsSignedInState(isSignedIn: Boolean)

    fun getIsSignedInState(): Boolean

    fun saveSettingsUserDataState(settingsUserData: SettingsUserData)

    fun getSettingsUserDataState(): SettingsUserData
}