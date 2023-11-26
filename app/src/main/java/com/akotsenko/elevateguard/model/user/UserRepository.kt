package com.akotsenko.elevateguard.model.user

import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.user.entities.User

class UserRepository(private val userSource: UserSource, private val appSettings: AppSettings) {

    suspend fun getUser(userId: String): User {
        return userSource.getUser(appSettings.getSettingsUserDataState().token, userId)
    }

    suspend fun updateUser(userId: String, user: User, password: String) {
        userSource.updateUser(appSettings.getSettingsUserDataState().token, userId, user, password)
    }

    suspend fun updateUserFacilityId(facilityId: Int) {
        val user = User(facilityId = facilityId)

        userSource.updateUser(appSettings.getSettingsUserDataState().token, appSettings.getSettingsUserDataState().userId.toString(), user, null)
    }

    suspend fun deleteUser(userId: String) {
        userSource.deleteUser(appSettings.getSettingsUserDataState().token, userId)
    }

    suspend fun getCurrentUser(): User {
        return userSource.getUser(appSettings.getSettingsUserDataState().token, appSettings.getSettingsUserDataState().userId.toString())
    }

    suspend fun updateCurrentUser(user: User, password: String?) {
        userSource.updateUser(appSettings.getSettingsUserDataState().token, appSettings.getSettingsUserDataState().userId.toString(), user, password)
    }
}