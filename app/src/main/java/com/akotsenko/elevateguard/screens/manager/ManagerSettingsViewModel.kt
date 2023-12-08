package com.akotsenko.elevateguard.screens.manager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akotsenko.elevateguard.Singletons
import com.akotsenko.elevateguard.model.auth.AuthRepository
import com.akotsenko.elevateguard.model.user.UserRepository
import com.akotsenko.elevateguard.model.user.entities.User
import com.akotsenko.elevateguard.screens.base.BaseViewModel
import com.akotsenko.elevateguard.utils.MutableUnitLiveEvent
import com.akotsenko.elevateguard.utils.publishEvent
import kotlinx.coroutines.launch

class ManagerSettingsViewModel(
    private val authRepository: AuthRepository = Singletons.authRepository,
    private val userRepository: UserRepository = Singletons.userRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user = _user

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            launchSignInScreen()
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            _user.value = userRepository.getCurrentUser()
        }
    }

    fun updateUser(user: User, password: String) {
        viewModelScope.launch {
            userRepository.updateCurrentUser(user, password)
        }
    }

}