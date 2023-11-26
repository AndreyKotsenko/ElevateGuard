package com.akotsenko.elevateguard.model.settings

import android.content.Context
import com.akotsenko.elevateguard.model.settings.entities.SettingsUserData
import com.google.gson.Gson

class SharedPreferencesAppSettings(
    appContext: Context
) : AppSettings {

    private val sharedPreferences =
        appContext.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    override fun saveIsSignedInState(isSignedIn: Boolean) {
        sharedPreferences.edit().putBoolean(IS_SIGNED_IN_STATE, isSignedIn).apply()
    }

    override fun getIsSignedInState(): Boolean = sharedPreferences.getBoolean(IS_SIGNED_IN_STATE, false)

    override fun saveSettingsUserDataState(settingsUserData: SettingsUserData) {
        sharedPreferences.edit().putString(SETTINGS_USER_DATA_STATE, Gson().toJson(settingsUserData)).apply()
    }

    override fun getSettingsUserDataState(): SettingsUserData =
        Gson().fromJson(sharedPreferences.getString(SETTINGS_USER_DATA_STATE, null), SettingsUserData::class.java)

    companion object {
        const val IS_SIGNED_IN_STATE = "IS_SIGNED_IN_STATE"
        const val SETTINGS_USER_DATA_STATE = "SETTINGS_USER_DATA_STATE"
    }
}