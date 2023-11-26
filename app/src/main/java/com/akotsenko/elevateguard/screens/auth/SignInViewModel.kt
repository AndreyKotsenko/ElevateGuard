package com.akotsenko.elevateguard.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.auth.AuthRepository
import com.akotsenko.elevateguard.model.auth.entities.Credential
import com.akotsenko.elevateguard.utils.MutableUnitLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent
import kotlinx.coroutines.launch


class SignInViewModel(private val authRepository: AuthRepository = Singletons.authRepository) :
    ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state

    private val _navigateToUserTabsEvent = MutableUnitLiveEvent()
    val navigateToUserTabsEvent = _navigateToUserTabsEvent

    private val _navigateToManagerTabsEvent = MutableUnitLiveEvent()
    val navigateToManagerTabsEvent = _navigateToManagerTabsEvent

    private val _navigateToEnterCompanyEvent = MutableUnitLiveEvent()
    val navigateToEnterCompanyEvent = _navigateToEnterCompanyEvent

    fun login(loginData: Credential) {
        viewModelScope.launch {
            showProgress()
            val account = authRepository.login(loginData)

            if(account.role == USER_ROLE) {
                launchUserTabsScreen()
            } else if(account.role == MANAGER_ROLE && account.facilityId == 0){
                launchEnterCompanyScreen()
            } else {
                launchManagerTabsScreen()
            }

            hideProgress()
        }
    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(signInInProgress = false)
    }

    private fun launchUserTabsScreen() = _navigateToUserTabsEvent.publishEvent()

    private fun launchManagerTabsScreen() = _navigateToManagerTabsEvent.publishEvent()

    private fun launchEnterCompanyScreen() = _navigateToEnterCompanyEvent.publishEvent()

    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }

    companion object {
        private const val USER_ROLE = "USER"
        private const val MANAGER_ROLE = "MANAGER"
    }
}