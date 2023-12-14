package com.akotsenko.elevateguard.screens.manager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.FacilityNotFoundException
import com.akotsenko.elevateguard.model.auth.AuthRepository
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.model.facility.entities.Facility
import com.akotsenko.elevateguard.model.settings.AppSettingsRepository
import com.akotsenko.elevateguard.model.settings.entities.SettingsUserData
import com.akotsenko.elevateguard.model.user.UserRepository
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.screens.auth.SignInViewModel
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.utils.MutableLiveEvent
import com.akotsenko.elevateguard.utils.MutableUnitLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent
import com.akotsenko.elevateguard.utils.share
import kotlinx.coroutines.launch

class ManagerSettingsViewModel(
    private val authRepository: AuthRepository = Singletons.authRepository,
    private val userRepository: UserRepository = Singletons.userRepository,
    private val appSettingsRepository: AppSettingsRepository = Singletons.appSettingsRepository,
    private val facilityRepository: FacilityRepository = Singletons.facilityRepository
) : BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state

    private val _user = MutableLiveData<SettingsUserData>()
    val user = _user

    private val _facilityName = MutableLiveData<String>()
    val facilityName = _facilityName

    private val _showFacilityNotFoundToastEvent = MutableLiveEvent<String>()
    val showFacilityNotFoundToastEvent = _showFacilityNotFoundToastEvent.share()

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            launchSignInScreen()
        }
    }

    fun getCurrentFacility() {
        _facilityName.value = appSettingsRepository.getFacilityName()
    }

    fun getCurrentUser() {
        _user.value = appSettingsRepository.getSettingsUserDataState()
    }

    fun updateUser(user: User, password: String?) {
        viewModelScope.launch {
            showProgress()
            userRepository.updateCurrentUser(user, password)
            var currentUserData = appSettingsRepository.getSettingsUserDataState()
            appSettingsRepository.saveSettingsUserDataState(toSettingsUserData(user, currentUserData))
            hideProgress()
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

    fun updateFacility(facilityName: String) {
        viewModelScope.launch {
            showProgress()
            try {
                facilityRepository.updateFacility(appSettingsRepository.getCurrentFacilityId().toString(), facilityName)
            } catch (e: FacilityNotFoundException) {
                processFacilityNotFoundException(e)
            }
            hideProgress()
        }
    }

    private fun processFacilityNotFoundException(e: FacilityNotFoundException) {
        _showFacilityNotFoundToastEvent.publishEvent(e.message.toString())
    }

    private fun showProgress() {
        _state.value = State(inProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(inProgress = false)
    }

    fun getUserRole(): String {
        return appSettingsRepository.getSettingsUserDataState().role
    }

    data class State(
        val inProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = inProgress
    }

}