package com.akotsenko.elevateguard.model.auth

import com.akotsenko.elevateguard.model.auth.entities.Account
import com.akotsenko.elevateguard.model.auth.entities.Credential
import com.akotsenko.elevateguard.model.auth.entities.RegisterData
import com.akotsenko.elevateguard.model.settings.AppSettings

class AuthRepository(private val authSource: AuthSource, private val appSettings: AppSettings) {


    fun isSignedIn(): Boolean {
        // user is signed-in if auth token exists
        return appSettings.getIsSignedInState()
    }

    suspend fun login(loginData: Credential): Account {
        val account = authSource.login(loginData)
        appSettings.saveSettingsUserDataState(account.toSettingsUserData())
        appSettings.saveIsSignedInState(true)

        return account
    }

    suspend fun logout(): String{
        appSettings.saveIsSignedInState(false)
        return authSource.logout(appSettings.getSettingsUserDataState().token)
    }

    suspend fun register(registerData: RegisterData): Account{
        return authSource.register(appSettings.getSettingsUserDataState().token, registerData)
    }

}