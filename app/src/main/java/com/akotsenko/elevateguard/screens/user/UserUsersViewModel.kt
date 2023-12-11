package com.akotsenko.elevateguard.screens.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.AuthException
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.screens.manager.ManagerUsersViewModel
import kotlinx.coroutines.launch

class UserUsersViewModel(private val facilityRepository: FacilityRepository = Singletons.facilityRepository) :
    BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users = _users

    private val _state = MutableLiveData(ManagerUsersViewModel.State())
    val state = _state

    fun getUsers() {
        viewModelScope.launch {
            showProgress()
            try {
                _users.value = facilityRepository.getUsersByFacility()
            } catch (e: AuthException) {
                launchSignInScreen()
            } finally {
                hideProgress()
            }
        }
    }


    private fun showProgress() {
        _state.value = ManagerUsersViewModel.State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(signInInProgress = false)
    }

    data class State(
        val inInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = inInProgress
    }
}