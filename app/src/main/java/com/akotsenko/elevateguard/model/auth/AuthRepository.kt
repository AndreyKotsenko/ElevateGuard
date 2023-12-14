package com.akotsenko.elevateguard.model.auth

import com.akotsenko.elevateguard.model.*
import com.akotsenko.elevateguard.model.auth.entities.Account
import com.akotsenko.elevateguard.model.auth.entities.Credential
import com.akotsenko.elevateguard.model.auth.entities.RegisterData
import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.wrapBackendExceptions

class AuthRepository(private val authSource: AuthSource, private val appSettings: AppSettings) {

    suspend fun login(loginData: Credential): Account {
        if (loginData.email.isBlank()) throw EmptyFieldException(Field.Email)
        if (loginData.password.isBlank()) throw EmptyFieldException(Field.Password)

        val account = try {
            authSource.login(loginData)
        } catch (e: BackendException) {
            if(e.code == 401 || e.code == 422) throw SignInValidateException(e.message.toString())
            else throw e
        }

        appSettings.saveSettingsUserDataState(account.toSettingsUserData())
        appSettings.saveIsSignedInState(true)

        return account
    }

    suspend fun logout(): String = wrapBackendExceptions {
        appSettings.saveIsSignedInState(false)
        return authSource.logout(appSettings.getSettingsUserDataState().token)
    }

    suspend fun register(registerData: RegisterData): Account = wrapBackendExceptions {
        if (registerData.userFirstName.isBlank()) throw EmptyFieldException(Field.FirstName)
        if (registerData.userLastName.isBlank()) throw EmptyFieldException(Field.LastName)
        if (registerData.userEmail.isBlank()) throw EmptyFieldException(Field.Email)
        if (registerData.userPassword.isBlank()) throw EmptyFieldException(Field.Password)
        if (registerData.userMobile.isBlank()) throw EmptyFieldException(Field.Mobile)

        val account = try {
            authSource.register(appSettings.getSettingsUserDataState().token, registerData)
        } catch (e: BackendException) {
            if(e.code == 422) throw RegisterValidateException(e.message.toString())
            else if (e.code == 400) throw FacilityNotFoundException(e.message.toString())
            else e
        }

        return account as Account
    }

}