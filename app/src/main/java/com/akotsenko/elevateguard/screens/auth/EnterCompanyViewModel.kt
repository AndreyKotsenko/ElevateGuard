package com.akotsenko.elevateguard.screens.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.model.user.UserRepository
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.utils.MutableUnitLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent
import com.akotsenko.elevateguard.utils.share
import kotlinx.coroutines.launch

class EnterCompanyViewModel(
    private val userRepository: UserRepository = Singletons.userRepository,
    private val facilityRepository: FacilityRepository = Singletons.facilityRepository
) : BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _navigateToManagerTabsEvent = MutableUnitLiveEvent()
    val navigateToManagerTabsEvent = _navigateToManagerTabsEvent

    fun createFacility(name: String) {
        viewModelScope.launch {
            showProgress()

            val facilityId = facilityRepository.createFacility(name = name)

            userRepository.updateUserFacilityId(facilityId)
            launchManagerTabsScreen()

            hideProgress()
        }

    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(signInInProgress = false)
    }

    private fun launchManagerTabsScreen() = _navigateToManagerTabsEvent.publishEvent()

    data class State(
        val emptyFieldError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
    }

}