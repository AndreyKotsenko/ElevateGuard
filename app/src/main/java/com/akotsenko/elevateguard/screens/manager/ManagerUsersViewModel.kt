package com.akotsenko.elevateguard.screens.manager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.auth.AuthRepository
import com.akotsenko.elevateguard.model.auth.entities.RegisterData
import com.akotsenko.elevateguard.model.facility.FacilityRepository
import com.akotsenko.elevateguard.model.user.UserRepository
import com.akotsenko.elevateguard.model.user.entities.User
import kotlinx.coroutines.launch

class ManagerUsersViewModel(
    private val userRepository: UserRepository = Singletons.userRepository,
    private val facilityRepository: FacilityRepository = Singletons.facilityRepository,
    private val authRepository: AuthRepository = Singletons.authRepository
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users = _users

    private val _state = MutableLiveData(State())
    val state = _state

    fun getUsers() {
        viewModelScope.launch {
            showProgress()
            _users.value = facilityRepository.getUsersByFacility()
            hideProgress()
        }
    }

    fun updateUser(
        userId: String,
        user: User,
        password: String
    ) {
        viewModelScope.launch {
            showProgress()
            userRepository.updateUser(userId, user, password)
            _users.value = facilityRepository.getUsersByFacility()
            hideProgress()
        }

    }

    fun createUser(registerData: RegisterData) {
        viewModelScope.launch {
            showProgress()
            authRepository.register(registerData)
            _users.value = facilityRepository.getUsersByFacility()
            hideProgress()
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            showProgress()
            userRepository.deleteUser(userId)
            _users.value = facilityRepository.getUsersByFacility()
            hideProgress()
        }
    }

    fun getCurrentFacilityId(): String {
        return facilityRepository.getCurrentFacilityId()
    }

    private fun showProgress() {
        _state.value = State(signInInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.value?.copy(signInInProgress = false)
    }

    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
    }
}