package com.akotsenko.elevateguard.model.user

import com.akotsenko.elevateguard.model.*
import com.akotsenko.elevateguard.model.settings.AppSettings
import com.akotsenko.elevateguard.model.user.entities.FacilityOfUser
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.model.wrapBackendExceptions

class UserRepository(private val userSource: UserSource, private val appSettings: AppSettings) {

    suspend fun getUser(userId: String): User = wrapBackendExceptions {
        return userSource.getUser(appSettings.getSettingsUserDataState().token, userId)
    }

    suspend fun updateUser(userId: String, user: User, password: String?) {
        wrapBackendExceptions {
            if (password?.isBlank() == true ) throw EmptyFieldException(Field.Password)
            if (user.firstName?.isBlank() == true) throw EmptyFieldException(Field.FirstName)
            if (user.lastName?.isBlank() == true) throw EmptyFieldException(Field.LastName)
            if (user.email?.isBlank() == true) throw EmptyFieldException(Field.Email)
            if (user.mobile?.isBlank() == true) throw EmptyFieldException(Field.Mobile)

            try {
                userSource.updateUser(appSettings.getSettingsUserDataState().token, userId, user, password)
            } catch (e: BackendException) {
                if (e.code == 422 || e.code == 400) throw UserValidateException(e.message.toString())
            }

        }
    }

    suspend fun updateUserFacilityId(facilityId: Int) {
        wrapBackendExceptions {
            val user = User(facilityId = facilityId)

            userSource.updateUser(appSettings.getSettingsUserDataState().token, appSettings.getSettingsUserDataState().userId.toString(), user, null)
        }
    }

    suspend fun deleteUser(userId: String) {
        wrapBackendExceptions {
            try {
                userSource.deleteUser(appSettings.getSettingsUserDataState().token, userId)
            } catch (e: BackendException) {
                if (e.code == 400) throw FailedDeleteUserException(e.message.toString())
            }
        }
    }

    suspend fun getCurrentUser(): User = wrapBackendExceptions {
        return userSource.getUser(appSettings.getSettingsUserDataState().token, appSettings.getSettingsUserDataState().userId.toString())
    }

    suspend fun updateCurrentUser(user: User, password: String?) {
        wrapBackendExceptions {
            updateUser(appSettings.getSettingsUserDataState().userId.toString(), user, password)
        }
    }

    suspend fun getFacilitiesOfUser(): List<FacilityOfUser> = wrapBackendExceptions {

        val facilities = try {
            userSource.getFacilitiesOfUser(appSettings.getSettingsUserDataState().token)
        } catch (e: BackendException) {
            if (e.code == 400) throw UserNotFoundException(e.message.toString())
            else e
        }
        return facilities as List<FacilityOfUser>
    }
}