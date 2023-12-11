package com.akotsenko.elevateguard.screens.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.AuthException
import com.akotsenko.elevateguard.model.EmptyFieldException
import com.akotsenko.elevateguard.model.auth.AuthRepository
import com.akotsenko.elevateguard.model.settings.AppSettingsRepository
import com.akotsenko.elevateguard.model.settings.entities.SettingsUserData
import com.akotsenko.elevateguard.model.user.UserRepository
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.screens.manager.ManagerSettingsViewModel
import com.akotsenko.elevateguard.utils.MutableUnitLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent
import kotlinx.coroutines.launch

class UserSettingsViewModel(
    private val authRepository: AuthRepository = Singletons.authRepository,
    private val userRepository: UserRepository = Singletons.userRepository,
    private val appSettingsRepository: AppSettingsRepository = Singletons.appSettingsRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user = _user

    private val _state = MutableLiveData(State())
    val state = _state

    private val _facilityName = MutableLiveData<String>()
    val facilityName = _facilityName

    private val _navigateToSignInEvent = MutableUnitLiveEvent()
    val navigateToSignInEvent = _navigateToSignInEvent

    fun getUser() {
        _user.value = appSettingsRepository.getSettingsUserDataState().toUser()
    }

    fun updateUser(user: User, password: String?) {
        viewModelScope.launch {
            try{
                showProgress()
                userRepository.updateCurrentUser(user, password)
                _user.value = userRepository.getCurrentUser()
                var currentUserData = appSettingsRepository.getSettingsUserDataState()
                appSettingsRepository.saveSettingsUserDataState(toSettingsUserData(user, currentUserData))
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: EmptyFieldException) {

            } finally {
                hideProgress()
            }
        }
    }

    private fun toSettingsUserData(user: User, currentUserData: SettingsUserData): SettingsUserData {
        return SettingsUserData(
            token = currentUserData.token,
            userId = currentUserData.userId,
            firstName = user.firstName!!,
            lastName = user.lastName!!,
            email = user.email!!,
            mobile =  user.mobile!!,
            isReceivePushNotification = user.isReceivePushNotification!!,
            isReceiveSmsNotification = user.isReceiveSmsNotification!!,
            isReceiveEmailNotification = user.isReceiveEmailNotification!!,
            positionId = currentUserData.positionId,
            role = currentUserData.role
        )
    }

    private fun showProgress() {
        _state.value = State(inProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(inProgress = false)
    }

    fun logout() {
        viewModelScope.launch {
            try{
                authRepository.logout()
                launchSignInScreen()
            } catch (e: AuthException) {
                launchSignInScreen()
            }
        }
    }

    fun getCurrentFacility() {
        _facilityName.value = appSettingsRepository.getFacilityName()
    }

    private fun launchSignInScreen() = _navigateToSignInEvent.publishEvent()

    data class State(
        val emptyConstructionNameError: Boolean = false,
        val inProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = inProgress
    }
}