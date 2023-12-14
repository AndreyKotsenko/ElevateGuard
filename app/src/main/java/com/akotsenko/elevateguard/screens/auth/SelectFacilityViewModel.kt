package com.akotsenko.elevateguard.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.AuthException
import com.akotsenko.elevateguard.model.UserNotFoundException
import com.akotsenko.elevateguard.model.settings.AppSettingsRepository
import com.akotsenko.elevateguard.model.user.UserRepository
import com.akotsenko.elevateguard.model.user.entities.FacilityOfUser
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.utils.MutableLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent
import kotlinx.coroutines.launch

class SelectFacilityViewModel(
    private val userRepository: UserRepository = Singletons.userRepository,
    private val appSettingsRepository: AppSettingsRepository = Singletons.appSettingsRepository
) : BaseViewModel() {

    private val _facilities = MutableLiveData<List<FacilityOfUser>>()
    val facilities = _facilities

    private val _state = MutableLiveData(State())
    val state = _state

    private val _showUserNotFoundEvent = MutableLiveEvent<String>()
    val showUserNotFoundEvent = _showUserNotFoundEvent

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    fun getFacilities() {
        viewModelScope.launch {
            try {
                showProgress()
                _facilities.value = userRepository.getFacilitiesOfUser()
                hideProgress()
            } catch (e: AuthException) {
                launchSignInScreen()
            } catch (e: UserNotFoundException) {

            }
        }
    }

    private fun processUserNotFoundException(e: UserNotFoundException) {
        _showUserNotFoundEvent.publishEvent(e.message.toString())
    }

    fun getUserRole(): String {
        return appSettingsRepository.getSettingsUserDataState().role
    }

    fun saveCurrentFacilityId(facilityId: Int) {
        appSettingsRepository.saveCurrentFacilityId(facilityId)
    }

    fun saveCurrentFacilityName(facilityName: String) {
        appSettingsRepository.saveFacilityName(name = facilityName)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(signInInProgress = false)
    }

    data class State(
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
    }
}